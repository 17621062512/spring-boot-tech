package com.ray.tech.controller;

import com.ray.tech.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/data-source")
public class DataSourceController {
    @Resource
    private DataSourceConfig dataSourceConfig;


    @GetMapping
    public void getDataSourceConfig() {

    }
}
