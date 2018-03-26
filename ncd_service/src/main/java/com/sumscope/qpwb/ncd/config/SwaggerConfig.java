package com.sumscope.qpwb.ncd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket ncdApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/wf/api.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sumscope webFast mmcfets service API")
                .description("Sumscope WebFast mmcfets info service")
                .termsOfServiceUrl("http://www.sumscope.com")
                .contact(new Contact("Sumscope FAT-WebBond Team", "http://www.sumscope.com",
                        "liangbin.liu@sumscope;mengyang.sun@sumscope.com;"))
                .license("供公司内部使用")
                .version("1.0")
                .build();
    }
}
