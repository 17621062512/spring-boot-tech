package com.ray.tech.service.observer.obs;

import com.ray.tech.service.observer.Observer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Ray implements Observer {
    private String name = "JuXinLi_Monitro";

    @Override
    public void update(String message) {
        log.info(name + " 收到:" + message);
    }
}
