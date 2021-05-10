package com.zt.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/5/5 18:10
 * @note:
 */
public class TestVod {


    public static void main(String[] args) throws Exception{

        //上传视频
        String accessKeyId="LTAI5tKSbrjRuatoVSMNs9Fk";
        String accessKeySecret="yHTcqZgnWTFxoRGkIxcOxefZ1Zmc1H";
        String title="Test Upload Video";
        String  fileName="E:/ZT/yu/Captures/6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }


    }


    //1.根据视频id获取视频播放地址
    public void getPlayUrl()  throws ClientException {

        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tKSbrjRuatoVSMNs9Fk", "yHTcqZgnWTFxoRGkIxcOxefZ1Zmc1H");

        //创建获取视频地址的request和response
        GetPlayInfoRequest request=new GetPlayInfoRequest();
        GetPlayInfoResponse response=new GetPlayInfoResponse();

        //向request对象里设置视频id
        request.setVideoId("f983d758bff94358b76ea900ae271f64");

        //调用初始化对象的方法，传递request，获取数据
        response=client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    //2.根据视频的id获取视频的播放凭证
    public void getPlayAuth()  throws ClientException{
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tKSbrjRuatoVSMNs9Fk", "yHTcqZgnWTFxoRGkIxcOxefZ1Zmc1H");
        //创建获取视频凭证request和response
        GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response=new GetVideoPlayAuthResponse();

        //向request设置视频id
        request.setVideoId("f983d758bff94358b76ea900ae271f64");

        //调用初始化对象的方法得到凭证

        response=client.getAcsResponse(request);
        System.out.println("PlayAuth"+response.getPlayAuth());
    }

}
