package com.ray.tech.model;

/**
 * @author JacobDong
 * @date 2018/5/8 16:48
 */
public enum ControllerFeedbackEnum {
    API_MONITOR_PLATFORM_INVOKE_SUCCESS("调用正常"), API_FILE_NOT_FOUND("未找到该文件"), API_INVOKE_FAILED("调用失败，%s");

    private String description;

    ControllerFeedbackEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
