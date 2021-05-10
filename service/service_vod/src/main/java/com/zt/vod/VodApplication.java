package com.zt.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ZhangTao
 * @date 2021/5/5 21:45
 * @note:
 */
@EnableDiscoveryClient//Nacos客户端注解
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//阻止访问数据库
@ComponentScan(basePackages = {"com.zt"})//扫描
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
    }

}
