package com.ray.tech.service.state;

import java.util.Random;

public class OutOfBalanceState implements State {
    AuthService authService;

    public OutOfBalanceState(AuthService authService) {
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
        System.out.println("余额不足");
//        throw new RuntimeException("余额不足");
        return false;
    }

    @Override
    public void failed() {
        System.out.println("授权失败");
    }
}
