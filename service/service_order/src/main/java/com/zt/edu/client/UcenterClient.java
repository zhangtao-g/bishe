package com.zt.edu.client;

import com.zt.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ZhangTao
 * @date 2021/5/14 21:02
 * @note:
 */
@Deprecated
@FeignClient("service-ucenter")
@Component
public interface UcenterClient {


    @PostMapping("/educenter/ucenter/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
