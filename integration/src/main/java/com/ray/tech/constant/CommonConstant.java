package com.ray.tech.constant;

public class CommonConstant {
    public static final String KEY_SUFFIX = "CAPTCHA_KEY";
    public static final String AUTHED_KEY_SUFFIX = "AUTH_KEY";
    public static final String BASE64_SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
    public static final int CAPTCHA_TIMEOUT = 3 * 60 * 1000;
    public static final long LOGIN_EXPIRATION = 24 * 60 * 60 * 1000L;

    private CommonConstant() {
        throw new IllegalStateException("Constant class");
    }
}
