package com.ray.tech.model;

import lombok.Data;

/**
 * @author JacobDong
 * @date 2018/5/15 17:32
 */
@Data
public class Passport {
    private String account;
    private String password;
    private String captcha;
    private String token;
}
