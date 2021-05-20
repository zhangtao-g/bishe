package com.zt.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.sun.org.apache.bcel.internal.classfile.Unknown;
import com.zt.commonutils.UnResult;
import com.zt.vod.service.VodService;
import com.zt.vod.utils.InitVodClient;
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
    //根据视频id获取视频的播放凭证

    /**
     *    String accessKeyId="LTAI5tKSbrjRuatoVSMNs9Fk";
     *     String accessKeySecret="yHTcqZgnWTFxoRGkIxcOxefZ1Zmc1H";
     * @param videoId
     * @return
     * @throws Exception
     */
    @GetMapping("getplayauth/{videoId}")
    public UnResult getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {

        //获取阿里云存储相关常量
        String accessKeyId = "LTAI5tKSbrjRuatoVSMNs9Fk";
        String accessKeySecret = "yHTcqZgnWTFxoRGkIxcOxefZ1Zmc1H";

        //初始化
        DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);

        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);

        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);

        //得到播放凭证
        String playAuth = response.getPlayAuth();

        //返回结果
        return UnResult.ok().message("获取凭证成功").data("playAuth", playAuth);
    }
}
