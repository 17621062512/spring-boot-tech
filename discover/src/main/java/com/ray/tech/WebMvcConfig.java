package com.ray.tech;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ray.tech.utils.GlobalUtils;
import com.ray.tech.utils.HttpMessageConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * @author zhangfan
 * web mvc配置
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter snakeCaseJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig snakeCaseJsonConfig = HttpMessageConverterUtils.getSnakeCaseJsonConfig();
        snakeCaseJsonConverter.setFastJsonConfig(snakeCaseJsonConfig);
        snakeCaseJsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        snakeCaseJsonConverter.setDefaultCharset(Charset.forName(GlobalUtils.DEFAULT_CHARSET));
        converters.add(snakeCaseJsonConverter);
        log.info("添加下划线支持");

        FastJsonHttpMessageConverter camelCaseJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig camelCaseJsonConfig = HttpMessageConverterUtils.getCamelCaseJsonConfig();
        camelCaseJsonConverter.setFastJsonConfig(camelCaseJsonConfig);
        MediaType camelCaseMediaType = new MediaType("application", "vnd");
        camelCaseJsonConverter.setSupportedMediaTypes(Collections.singletonList(camelCaseMediaType));
        camelCaseJsonConverter.setDefaultCharset(Charset.forName(GlobalUtils.DEFAULT_CHARSET));
        converters.add(camelCaseJsonConverter);
        log.info("添加驼峰支持");
    }


}
