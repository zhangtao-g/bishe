package com.zt.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.commonutils.JwtUtils;
import com.zt.commonutils.UnResult;
import com.zt.edu.entity.Order;
import com.zt.edu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-14
 */


@RestController
@RequestMapping("/eduorder/order")
public class OrderController {


    @Autowired
    private OrderService orderService;



    //创建订单返回订单号JwtUtils.getMemberIdByJwtToken(request)
    @PostMapping("createOrder/{courseId}")
    public UnResult saveOrder(@PathVariable String courseId , HttpServletRequest request){
        //创建订单，返回订单号
        String orderId=orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));

        System.out.println(orderId);
            return UnResult.ok().data("orderId",orderId) ;
        }


//     //根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public UnResult getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order=orderService.getOne(wrapper);

//        System.out.println(order);
        return UnResult.ok().data("items",order);
    }

    //3.根据课程Id和用户ID查询订单表中的订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("status",1);
        int count = orderService.count(queryWrapper);
        if (count>0){//已经支付了
            return true;
        }else {
            return false;
        }
    }

    }


