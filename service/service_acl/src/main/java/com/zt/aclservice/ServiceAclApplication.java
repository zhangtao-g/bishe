package com.zt.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
//exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class}
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.zt")
@MapperScan("com.zt.aclservice.mapper")
public class ServiceAclApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAclApplication.class, args);
    }

}
