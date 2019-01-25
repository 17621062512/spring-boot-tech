package com.ray.tech.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "base_task")
public class BaseTaskPo {
    @Id
    private String id;
    @Indexed
    private String category;
    private String token;
    private String name;
    private String idCardNum;
    private String cellPhoneNum;
    private String website;
    private String userId;
    private String account;
    @Indexed
    private String organizationAccount;
    @Indexed
    private long createTime;
    private String devicePlatform;
    private String deviceType;
    private String clientIp;
    private String netType;
    private String invokeChannel;
    private String marketChannel;
    private String comment;
    private String message;
    private String version;
    private AuthorizeTrackPointEnum authorizeTrackPoint;
    private String websiteName;
}
