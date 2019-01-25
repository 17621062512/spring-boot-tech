package com.ray.tech.model;

/**
 * @author JacobDong
 * @date 2018/5/8 16:48
 */
public enum ControllerFeedbackEnum {
    API_MONITOR_PLATFORM_INVOKE_SUCCESS("调用正常"), API_CAPTCHA_GET_FAIL("获取验证码失败");

    private String description;

    ControllerFeedbackEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
