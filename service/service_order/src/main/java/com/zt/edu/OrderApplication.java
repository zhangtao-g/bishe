package com.zt.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ZhangTao
 * @date 2021/5/14 16:37
 * @note:
 */

@EnableFeignClients//他要调用edu和ucenter
@EnableDiscoveryClient//Nacos客户端注解
@SpringBootApplication
@ComponentScan(basePackages = {"com.zt"})//为了能够扫描到common中service_base中的包
@MapperScan("com.zt.edu.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}