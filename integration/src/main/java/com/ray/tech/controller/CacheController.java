package com.ray.tech.controller;

import com.ray.tech.config.CacheConfig;
import com.ray.tech.model.ApiResponse;
import com.ray.tech.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController {
    @Resource
    private CaffeineCacheManager caffeineCacheManager;
    @Resource
    private CommonService commonService;

    @GetMapping
    public ApiResponse getCachedData(@RequestParam("key") String key) {
        return commonService.getCachedData(key);
    }

    @GetMapping("/manager")
    public void getManager() {
        Cache cache = caffeineCacheManager.getCache(CacheConfig.CACHE_NAME);
        String name = cache.getName();
        log.info(name);
    }
}
