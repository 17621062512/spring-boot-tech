package com.ray.tech.service.observer;

import lombok.Data;

public interface Observer {
    String name = "";

    void update(String message);

    default String getName() {
        return this.name;
    }

    ;
}
