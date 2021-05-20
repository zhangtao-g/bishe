package com.zt.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ZhangTao
 * @date 2021/5/16 22:08
 * @note:
 */
@Deprecated
@Component
@FeignClient("service-order")
public interface OrdersClient {


    //3.根据课程Id和用户ID查询订单表中的订单状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
