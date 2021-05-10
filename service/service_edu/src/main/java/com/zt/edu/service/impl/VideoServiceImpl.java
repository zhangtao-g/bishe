package com.zt.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.edu.client.VodClient;
import com.zt.edu.entity.Video;
import com.zt.edu.mapper.VideoMapper;
import com.zt.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    ///根据课程id删除小节，并删除相应的视频
    @Override
    public void removeVideoBycourseId(String id) {
//        根据视频id查询课程所有的视频id
        QueryWrapper<Video> wrappervideo=new QueryWrapper<>();
        wrappervideo.eq("course_id",id);
        wrappervideo.select("video_source_id");//只查询video_sourse_id
        List<Video> videolist=baseMapper.selectList(wrappervideo);

        //List<Video>变成List<String>,去掉对象
        List<String> videoIds=new ArrayList<>();

        for(int i=0;i<videolist.size();i++){
            //先列出一个，一个一个的查videoSourseId
            Video video=videolist.get(i);
            String videoSourseId=video.getVideoSourceId();

            //不为空才添加
            if(!StringUtils.isEmpty(videoSourseId)){
                //全都放到list-String集合里
                videoIds.add(videoSourseId);
            }

        }

//        videoIds里面有值才删除
        if(videoIds.size()>0){
            //删除
            vodClient.deleteBatch(videoIds);
        }



        QueryWrapper<Video> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    };
}
