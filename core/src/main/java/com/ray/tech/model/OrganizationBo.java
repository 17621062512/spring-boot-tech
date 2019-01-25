package com.ray.tech.model;

import lombok.Data;

/*
 * 机构业务表
 * @author JacobDong
 * @date 2018/4/24 18:11
 */
@Data
public class OrganizationBo {
    /**
     * 机构数据库主键
     */
    private String id;
    /**
     * 机构名称
     */
    private String name;
    /**
     * 机构账号
     */
    private String account;
    /**
     * 机构报告版本号
     */
    private String reportVersion;
    /**
     * 机构log地址
     */
    private String logUrl;

    /**
     * 过期时间
     */
    private long expireTime;

    /**
     * 机构秘钥
     */
    private String apiKey;

    /**
     * 机构状态
     * NORMAL,EXPIRED,FROZEN
     */
    private OrganizationStatus status;
}
