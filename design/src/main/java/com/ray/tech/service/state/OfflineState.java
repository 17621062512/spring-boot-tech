package com.ray.tech.service.state;

public class OfflineState implements State {
    AuthService authService;

    public OfflineState(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void auth() {
        System.out.println("无法授权,数据源已经下线");
    }

    @Override
    public boolean success() {
        System.out.println("无法授权,数据源已经下线");
        return false;
    }

    @Override
    public void failed() {
        System.out.println("无法授权,数据源已经下线");
    }
}
