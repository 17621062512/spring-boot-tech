package com.ray.tech.model;

import lombok.Data;

@Data
public class Notification {
    private String openId;
    private String title = "";
    private String[] rows;
    private String summary = "";
    private String targetUrl = "";
    private NotificationType type;


    public enum NotificationType {
        INFO,
        WARNING
    }
}
