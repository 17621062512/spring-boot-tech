package com.ray.tech.service.decorator;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public abstract class Report {
    JSONObject data = new JSONObject();
    private String name = "未知报告";
    private double cost = 1.0;

    public abstract void addData();

    public abstract void cost();
}
