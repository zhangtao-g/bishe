package com.zt.edu.schedule;

import com.zt.edu.service.StatisticsDailyService;
import com.zt.edu.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.StandardSocketOptions;
import java.util.Date;

/**
 * @author ZhangTao
 * @date 2021/5/17 18:22
 * @note:
 *
 * corn表达式七子表达式
 * 在线生成网站https://www.pppet.net/
 *
 *
 */

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;
    /**
     * 测试
     * 每天七点到二十三点每五秒执行一次
     */
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1() {
//        System.out.println("*********++++++++++++*****执行了");
//    }
    /**
     * 每天凌晨一点执行,把前一天的数据写入数据库
     * 0 0 1 * * ?
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {

        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.countRegister(day);
    }
}
