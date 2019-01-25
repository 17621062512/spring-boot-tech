package com.ray.tech.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by jacobdong on 2017/4/5.
 */
@Data
@Document(collection = "subscribe_user")
public class SubscribeUser {
    @Id
    private String openId;
    private String headImgUrl;
    private String realName;
    private String nickName;
    private String intro = "世界那么大，我想去看看";
    private String phone;
    private Long createTime = System.currentTimeMillis();
    private Long updateTime = System.currentTimeMillis();
    private boolean subscribe = true;
    private List<String> tags = new ArrayList<>();
    private String lastLocation;
    private long lastOperationTime;
    private Set<String> shareTickets = new HashSet<>();
    private int like;
    private Set<String> bindMerchant;

    public void addShareTicket(String ticket) {
        this.shareTickets.add(ticket);
    }

    public void like() {
        this.like++;
    }
}
