package com.zt.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * <p>
 * 处理跨域
 * </p>
 *
 * @author ZahngTao
 * @since 2021-05-18
 */

/**
 * description:
 *
 * @author ZhangTao
 * @since 2021-05-18
 * @note 如果使用此配置类，将使用此注解，将项目中的所有controller中的跨域注解注释掉
 *
 * ，否则使用原始的方式添加跨域注解，两个不能同时使用
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}

