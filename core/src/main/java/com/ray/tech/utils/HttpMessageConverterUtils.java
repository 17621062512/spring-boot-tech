package com.ray.tech.utils;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.support.config.FastJsonConfig;

import java.nio.charset.Charset;

public class HttpMessageConverterUtils {

    public static FastJsonConfig getCamelCaseJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
        fastJsonConfig.setCharset(Charset.forName(GlobalUtils.DEFAULT_CHARSET));
        return fastJsonConfig;
    }

    public static FastJsonConfig getSnakeCaseJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        fastJsonConfig.setSerializeConfig(serializeConfig);
        fastJsonConfig.setCharset(Charset.forName(GlobalUtils.DEFAULT_CHARSET));
        return fastJsonConfig;
    }
}
