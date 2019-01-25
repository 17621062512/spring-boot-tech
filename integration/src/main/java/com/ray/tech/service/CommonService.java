package com.ray.tech.service;

import com.ray.tech.config.CacheConfig;
import com.ray.tech.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CommonService {
    @Cacheable(CacheConfig.CACHE_NAME)
    public ApiResponse getCachedData(String key) {
        log.info(key);
        String s = RandomStringUtils.randomAlphabetic(32);
        log.info(s);
        return new ApiResponse<>(s);
    }

//        @Metered(name = "Method-test")
//@ExceptionMetered(name = "Method-test")
//@Gauge(name = "Method-test")


//    @Timed(name = "Method-test")
//    @Counted(name = "Method-test")
    @Async(value = CacheConfig.CACHE_NAME)
    public void timerTestMethod() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println("I:" + i);

            Thread.sleep(100L);
        }
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }
}
