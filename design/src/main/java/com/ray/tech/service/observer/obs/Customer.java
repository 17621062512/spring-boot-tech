package com.ray.tech.service.observer.obs;

import com.ray.tech.service.observer.Observer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Customer implements Observer {
    protected String name;

    @Override
    public void update(String message) {
        log.info(name + " 收到:" + message);
    }

    public Customer(String orgName) {
        this.name = orgName;
    }

    public Customer() {
    }
}
