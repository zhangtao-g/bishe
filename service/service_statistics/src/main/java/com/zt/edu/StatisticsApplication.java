package com.zt.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ZhangTao
 * @date 2021/4/8 21:46
 * @note:
 *
 * 因为在service上的pom加的注册中心所以。
 * 就算这个服务（service子服务）没有配置，启动的时候会默认去找
 * 注册中心，所以这个oss也要配置上ancos
 *
 */



@EnableDiscoveryClient//Nacos客户端注解

@EnableFeignClients//调用端注解
@SpringBootApplication
@ComponentScan(basePackages = {"com.zt"})//为了能够扫描到common中service_base中的包
@MapperScan("com.zt.edu.mapper")
@EnableScheduling//开启定时任务
public class StatisticsApplication{
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }
}
