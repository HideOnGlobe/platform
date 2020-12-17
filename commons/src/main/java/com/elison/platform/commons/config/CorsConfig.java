package com.elison.platform.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: monitor
 * @Package: com.elison.platform.commons.config
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/11/6 11:41
 * @UpdateDate: 2020/11/6 11:41
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.origin}")
    private String origin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("OPTIONS", "POST", "GET", "PUT", "DELETE", "PATCH")
                .allowedOrigins(origin.split(","))
                .allowCredentials(true);
    }
}
