package com.zt.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.edu.entity.Chapter;
import com.zt.edu.entity.Video;
import com.zt.edu.entity.chapter.ChapterVo;
import com.zt.edu.entity.chapter.VideoVo;
import com.zt.edu.mapper.ChapterMapper;
import com.zt.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.edu.service.VideoService;
import com.zt.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    //根据课程id查询进行查询


    @Autowired
    private VideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1根据课程id查询所有章节
        QueryWrapper<Chapter> wrapperChapter= new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);//这个双引号内的不能用驼峰命名法！！！！
        List<Chapter> chapters=baseMapper.selectList(wrapperChapter);

        //2.根据课程id查询里面所有的小节
        QueryWrapper<Video> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<Video> videos=videoService.list(wrapperVideo);

        //设置一个list集合，用来存放最终的封装数据
        List<ChapterVo> finallist=new ArrayList<>();

        //3.遍历查询章节list集合进行封装
        for (int i = 0; i < chapters.size(); i++) {

            //获取到每个章节
            Chapter chapter = chapters.get(i);
            //创建Vo对象
            ChapterVo chapterVo = new ChapterVo();
            //将获取到的每个章节，传到VO中
            BeanUtils.copyProperties(chapter, chapterVo);
            finallist.add(chapterVo);

            //设置一个list集合，用来存放小节的封装数据
            List<VideoVo> finalVideo = new ArrayList<>();

            for (int j = 0; j < videos.size(); j++) {
                Video video = videos.get(j);

                //小节的capterid是否等于章节的id
                if (video.getChapterId().equals(chapterVo.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    finalVideo.add(videoVo);
                }

            }
            chapterVo.setChildren(finalVideo);
        }



        return finallist;


    }
    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid查小节,有小节不能删除，否则能删除
        QueryWrapper<Video> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count=videoService.count(wrapper);
        if(count>0){//有小节
            throw  new GuliException(20001,"章节下不为空，不能删除");
        }else{//没有小节
            //删除
            int result=baseMapper.deleteById(chapterId);
            //成功
            return result>0;
        }


    }

    @Override
    public void removeChapterBycourseId(String id) {
        //根据课程id删除章节
        QueryWrapper<Chapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);


    }
}
