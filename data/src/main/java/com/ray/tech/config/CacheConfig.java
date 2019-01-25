package com.ray.tech.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.google.common.collect.Lists;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    public static final String WEBSITES_2_DATA_SOURCE_CACHE = "Website_2_DataSource_Cache";

    /**
     * @return
     * @See 所有配置:{@link CaffeineSpec#configure(String, String)}
     */
    @Bean(name = WEBSITES_2_DATA_SOURCE_CACHE)
    public CaffeineCacheManager cacheManager() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(500)
                .expireAfterWrite(20, TimeUnit.MINUTES);
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.setCacheNames(Lists.newArrayList(WEBSITES_2_DATA_SOURCE_CACHE));

        return caffeineCacheManager;
    }
}
