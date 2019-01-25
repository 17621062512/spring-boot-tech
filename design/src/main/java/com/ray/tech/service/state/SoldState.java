package com.ray.tech.service.state;

import java.util.Random;

public class SoldState implements State {
    AuthService authService;

    public SoldState(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void auth() {
        System.out.println("开始授权");
        if (new Random().nextBoolean()) {
            authService.success();
        } else {
            authService.failed();
        }
    }

    @Override
    public boolean success() {
        System.out.println("授权成功");
        return true;
    }

    @Override
    public void failed() {
        System.out.println("授权失败");
    }
}
