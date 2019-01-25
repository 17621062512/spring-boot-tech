package com.ray.tech.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/log")
public class TempController {

    @PostMapping
    public String getNoti(@RequestBody Map map) {
        log.info(JSON.toJSONString(map));
        return "OK";
    }
}
