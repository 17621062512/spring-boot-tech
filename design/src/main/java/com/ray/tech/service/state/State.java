package com.ray.tech.service.state;

public interface State {

    void auth();

    boolean success();

    void failed();
}
