package com.elison.platform.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.config
 * @Description: 处理由于Controller返回String,交给ResponseResultBodyAdvice进行处理时
 * 在组装ResponseHeader中由于处理String的HttpMessageConverter较为靠前,导致Result(String)交给了StringHttpMessageConverter处理
 * 本方法的目的是为了能先行处理Object的HttpMessageConverter
 * @Author: elison
 * @CreateDate: 2020/12/1 10:12
 * @UpdateDate: 2020/12/1 10:12
 **/
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
