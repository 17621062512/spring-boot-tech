package com.ray.tech.constant;

/**
 * 全局异常
 * 会被外部接口在 advice 中进行拦截
 *
 * @author JacobDong
 * @date 2018/5/10 17:17
 */
public enum GlobalControllerFeedbackEnum {
    /**
     * 参数异常
     */
    GLOBAL_EXCEPTION_PARAMETERS_INVALID("请求参数错误"),
    /**
     * 没有访问权限
     */
    GLOBAL_EXCEPTION_PERMISSION_DENY("鉴权失败"),
    /**
     * 内部服务异常
     */
    GLOBAL_EXCEPTION_INTERNAL_SYSTEM_ERROR("系统服务异常 %s");

    private String message;

    GlobalControllerFeedbackEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
