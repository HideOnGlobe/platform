package com.elison.platform.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.center.config
 * @Description: -Swagger2配置类
 * @Author: elison
 * @CreateDate: 2019/9/7 22:26
 * @UpdateDate: 2019/9/7 22:26
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.elison.platform"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Platform")
                //描述
                .description("接口文档")
                .contact(new Contact("elison", "", "hideonglobe@gmail.com"))
                //创建人
                //版本号
                .version("1.0")
                .build();
    }

}
