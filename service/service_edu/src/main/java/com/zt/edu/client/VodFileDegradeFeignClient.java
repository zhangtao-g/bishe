package com.zt.edu.client;

import com.zt.commonutils.UnResult;
import org.springframework.stereotype.Component;
import sun.reflect.generics.tree.VoidDescriptor;

import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/5/7 21:45
 *
 *
 * 前面需要配置pom依赖和配置文件
 * @note:熔断机制，若发生熔断则调用此方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public UnResult removeVideo(String videoId) {
        return UnResult.error().message("熔断：小节中删除视频出错==>time out");
    }

    @Override
    public UnResult deleteBatch(List<String> videoList) {
        return UnResult.error().message("熔断：章节中删除多个视频出错==>time out");

    }


}
