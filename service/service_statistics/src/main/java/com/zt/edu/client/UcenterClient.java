package com.zt.edu.client;

import com.zt.commonutils.UnResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ZhangTao
 * @date 2021/5/17 16:10
 * @note:
 */

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //查询某一天的人数
    @GetMapping("/educenter/ucenter/countRegister/{day}")
    public UnResult countRegister(@PathVariable("day") String day);


}
