package com.ray.tech.exception;

/**
 * @author zhangfan
 * 用户输入参数异常
 */
public class ArgumentException extends RuntimeException {
    /**
     * 字段
     */
    private String message;

    public ArgumentException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
