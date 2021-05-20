package com.zt.edu.controller;


import com.zt.commonutils.UnResult;
import com.zt.edu.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-14
 */

@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {


    @Autowired
    private PayLogService payLogService;
    //生成微信支付二维码
    @GetMapping("createNative/{orderNo}")
    public UnResult createNative(@PathVariable String orderNo){
        //返回信息，包含二维码地址，还有其他需要的信息
        Map map = payLogService.createNative(orderNo);
        System.out.println("************返回二维码map集合"+map);
        return UnResult.ok().data(map);
    }

    //查询订单支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public UnResult queryPayStatus(@PathVariable String orderNo){
        //返回信息，包含二维码地址，还有其他需要的信息
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("############二维码信息map集合"+map);
        if (map == null){
            return UnResult.error().message("支付出错了");
        }
        //如果不为空，通过map获取订单状态;
        if ("SUCCESS".equals(map.get("trade_state"))){
            //支付成功，更改订单状态
            payLogService.updateOrderStatus(map);
            return UnResult.ok().message("支付成功");
        }

        return UnResult.error().code(25000).message("支付中");
    }


}

