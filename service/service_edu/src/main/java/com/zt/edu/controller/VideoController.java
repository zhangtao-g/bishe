package com.zt.edu.controller;


import com.zt.commonutils.UnResult;
import com.zt.edu.client.VodClient;
import com.zt.edu.entity.Video;
import com.zt.edu.service.VideoService;
import com.zt.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class VideoController {


    //注入通过nacos配置的vodClient
    @Autowired
    private VodClient vodClient;

    @Autowired
    private VideoService videoService;



    //添加小节
    @PostMapping("addVideo")
    public UnResult addVideo(@RequestBody Video video){
        System.out.println("这里的vide是"+video);
       videoService.save(video);
        return UnResult.ok();
    }

    //删除小节以及视频
    @DeleteMapping("{id}")
    public UnResult deleteVideo(@PathVariable String id){

        System.out.println(id);
        //删除视频
        //根据小节id找到视频id,再传入
       Video video= videoService.getById(id);
        System.out.println(video);
       String videoSourceId=video.getVideoSourceId();
        System.out.println(videoSourceId);

       if(!StringUtils.isEmpty(videoSourceId)){
           UnResult result = vodClient.removeVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，熔断器......");
            }

       }

       //删除小节
        videoService.removeById(id);
        return UnResult.ok();

    }



}

