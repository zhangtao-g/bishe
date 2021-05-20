package com.zt.edu.controller;


import com.zt.commonutils.UnResult;
import com.zt.edu.service.StatisticsDailyService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-17
 */
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //统计一天的注册人数,生成统计数据
    @PostMapping("registerCount/{day}")
    public UnResult registerCount(@PathVariable String day){
        statisticsDailyService.countRegister(day);
        return UnResult.ok();
    }

    //显示页面图表
    @GetMapping("getShowData/{type}/{begin}/{end}")
    public UnResult getShowData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = statisticsDailyService.getShowData(type,begin,end);
        return UnResult.ok().data(map);
    }

}

