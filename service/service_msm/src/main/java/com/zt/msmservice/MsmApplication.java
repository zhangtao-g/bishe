package com.zt.msmservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ZhangTao
 * @date 2021/5/10 22:01
 * @note:阿里云短信服务
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//阻止访问数据库
@ComponentScan(basePackages = {"com.zt"})//扫描
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class,args);
    }

}
