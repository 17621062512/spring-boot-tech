package com.ray.tech.model;

/**
 * @author JacobDong
 * @date 2018/5/14 21:17
 */
public enum AuthorizeTrackPointEnum {
    /**
     * 尝试登陆
     */
    ATTEMPT_LOGIN,
    /**
     * 授权成功
     */
    AUTHORIZE_SUCCESS,
    /**
     * 授权失败
     */
    AUTHORIZE_FAIL,
    /**
     * 重置密码失败
     */
    RESET_PASSWORD_SUCCESS,
    /**
     * 重置密码成功
     */
    RESET_PASSWORD_FAIL,
    /**
     * 采集成功
     */
    COLLECT_SUCCESS,
    /**
     * 采集失败
     */
    COLLECT_FAIL,
    /**
     * 解析成功
     */
    PARSE_SUCCESS,
    /**
     * 解析失败
     */
    PARSE_FAIL,
    /**
     * 解析失败，没有有效数据
     */
    HAS_NO_VALID_DATA,
    /**
     * 报告成功
     */
    REPORT_SUCCESS,
    /**
     * 报告失败
     */
    REPORT_FAIL
}
