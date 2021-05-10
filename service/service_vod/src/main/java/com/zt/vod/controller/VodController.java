package com.zt.vod.controller;

import com.sun.org.apache.bcel.internal.classfile.Unknown;
import com.zt.commonutils.UnResult;
import com.zt.vod.service.VodService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/5/5 21:50
 * @note:
 */
@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {


    @Autowired
    private VodService vodService;

    //上传至阿里云视频
    @PostMapping("uploadVideo")
    public UnResult uploadAliyunVideo(MultipartFile file) {

        String videoId=vodService.uploadVideo(file);
        return UnResult.ok().data("videoId",videoId);
    }



    //根据id删除视频
    @DeleteMapping("removeVideo/{id}")
    public UnResult removeVideo(@PathVariable String id){

        vodService.removeAliYunVideo(id);

        return UnResult.ok();
    }


    //删除多个视频的方法
    @DeleteMapping("deleteBatch")
    public UnResult deleteBatch(@RequestParam("videoList") List<String> videoList){
        vodService.removeBatchVideo(videoList);
            return UnResult.ok();
    }
}
