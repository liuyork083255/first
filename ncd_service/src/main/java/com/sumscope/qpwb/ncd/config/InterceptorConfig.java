package com.sumscope.qpwb.ncd.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.sumscope.qpwb.ncd.interceptor.NcdInterceptor;
import com.sumscope.service.webbond.common.web.interceptor.RequestTimingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by liu.yang on 2018/1/10.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new NcdInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }

    /**
     * 修改ObjectMapper默认配置
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter mappingJsonConverter = (MappingJackson2HttpMessageConverter) converter;
                mappingJsonConverter.getObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            }
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
