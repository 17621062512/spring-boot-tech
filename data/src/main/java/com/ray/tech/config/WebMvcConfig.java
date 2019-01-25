package com.ray.tech.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ray.tech.interceptor.AccessInterceptor;
import com.ray.tech.utils.GlobalUtils;
import com.ray.tech.utils.HttpMessageConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.http.ActuatorMediaType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private AccessInterceptor accessInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter snakeCaseJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig snakeCaseJsonConfig = HttpMessageConverterUtils.getSnakeCaseJsonConfig();
        snakeCaseJsonConfig.setSerializerFeatures(WriteMapNullValue,
                WriteNullListAsEmpty,
                WriteNullStringAsEmpty);
        snakeCaseJsonConverter.setFastJsonConfig(snakeCaseJsonConfig);
        snakeCaseJsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        snakeCaseJsonConverter.setDefaultCharset(Charset.forName(GlobalUtils.DEFAULT_CHARSET));
        converters.add(snakeCaseJsonConverter);
        log.debug("添加下划线支持");
    }

    @Bean
    public MappingJackson2HttpMessageConverter actuatorHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(
                Jackson2ObjectMapperBuilder.json().featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build());
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.parseMediaType(ActuatorMediaType.V2_JSON)));
        return converter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        匹配路径拦截
        registry.addInterceptor(accessInterceptor)
                .excludePathPatterns("/actuator/**")
                .excludePathPatterns("/**/granter/data")
                .addPathPatterns("/**");

    }
}
