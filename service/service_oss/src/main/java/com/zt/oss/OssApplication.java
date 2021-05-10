package com.zt.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ZhangTao
 * @date 2021/4/21 11:14
 * @note:
 *
 * 设置好了再配置文件中没有配置数据源的信息，运行报错
 * Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
 *exclude = DataSourceAutoConfiguration.class
 * 将数据源配置移除
 *
 *
 */

@EnableDiscoveryClient//Nacos客户端注解
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.zt"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
