package com.zt.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.zt.servicebase.exceptionhandler.GuliException;
import com.zt.vod.service.VodService;
import com.zt.vod.utils.ConstantVodUtils;
import com.zt.vod.utils.InitVodClient;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/5/5 21:52
 * @note:
 */

@Service
public class VodServiceImpl implements VodService {

    /**
     * 通过流式上传
     * @param file
     * @return
     */

    String accessKeyId="LTAI5tKSbrjRuatoVSMNs9Fk";
    String accessKeySecret="yHTcqZgnWTFxoRGkIxcOxefZ1Zmc1H";


    //上传视频的具体方法
    @Override
    public String uploadVideo(MultipartFile file){
        //上传视频

        String  fileName=file.getOriginalFilename();//取原始名
        System.out.println(fileName);
        String title=fileName.substring(0,fileName.lastIndexOf("."));//取fileName的mp4之前的前缀
        System.out.println(title);
        System.out.println(file);


        try {
            InputStream inputStream=file.getInputStream();
            //创建初始化对象
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            System.out.println("request"+request);
            UploadVideoImpl uploader = new UploadVideoImpl();
            System.out.println("uploader"+uploader);
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId=null;
            System.out.println("response"+response);

//            videoId=response.getVideoId();
            if (response.isSuccess()) {
                videoId=response.getVideoId();

            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId=response.getVideoId();

            }
            System.out.println(videoId);
            return videoId;


        }catch (IOException e){
            e.printStackTrace();
            return null;
        }




    }

    @Override
    public void removeAliYunVideo(String id) {


        try {
            ///初始化对象
            DefaultAcsClient client= InitVodClient.initVodClient(accessKeyId,accessKeySecret);

            //创建删除对象的request
            DeleteVideoRequest request=new DeleteVideoRequest();

            //设置id
            request.setVideoIds(id);

            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();

            throw new GuliException(20001,"删除视频失败");
        }

    }


    //删除多个视频id
    @Override
    public void removeBatchVideo(List videoList) {

        try {
            ///初始化对象
            DefaultAcsClient client= InitVodClient.initVodClient(accessKeyId,accessKeySecret);
            //创建删除对象的request
            DeleteVideoRequest request=new DeleteVideoRequest();

            //将数组中的值按 ","分割开
            //join这个方法不错
            String videoId= StringUtil.join(videoList.toArray(),",");
            //设置id
            request.setVideoIds(videoId);

            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();

            throw new GuliException(20001,"删除多条视频失败");
        }

    }
}
