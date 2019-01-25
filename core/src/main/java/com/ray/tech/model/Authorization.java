package com.ray.tech.model;

import lombok.Data;

/**
 * 外部鉴权所用的视图层对象
 * Created by jacobdong on 15/7/17.
 */
@Data
public class Authorization<T> {

    private String token;
    private long signInTime;
    private AuthType type = AuthType.ORIGANIZATION;
    private T info;
    private String authorization;

    public enum AuthType {
        ORIGANIZATION,
        STAFF
    }

}
