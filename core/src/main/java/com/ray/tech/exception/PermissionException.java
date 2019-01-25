package com.ray.tech.exception;

/**
 * @author JacobDong
 * @date 2018/5/8 10:07
 */
public class PermissionException extends RuntimeException {
    /**
     * 字段
     */
    private String message;

    public PermissionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
