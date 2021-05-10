package com.zt.edu.client;

import com.zt.commonutils.UnResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/5/7 14:55
 * @note:
 */

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)//fallback熔断返回
@Component
public interface VodClient {

    //定义调用的方法路径
        //根据视频id删除视频
    @DeleteMapping("/eduvod/video/removeVideo/{id}")//路径为完全路径
    public UnResult removeVideo(@PathVariable("id") String id);//PathVariable一定要指定参数名称

    //删除多个视频的方法
    @DeleteMapping("/eduvod/video/deleteBatch")
    public UnResult deleteBatch(@RequestParam("videoList") List<String> videoList);

}
