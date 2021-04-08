package com.zt.edu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangTao
 * @date 2021/4/8 21:50
 * @note:配置类
 */
@Configuration
@MapperScan("com.zt.edu.mapper")////在每个mapper中添加注解@Mapper也行
public class TeaConfig {

}
