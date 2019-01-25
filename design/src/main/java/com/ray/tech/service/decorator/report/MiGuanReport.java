package com.ray.tech.service.decorator.report;

import com.alibaba.fastjson.JSONObject;
import com.ray.tech.service.decorator.Report;

public class MiGuanReport extends Report {
    private static final double MG_COST = 1.1;

    public MiGuanReport() {
        this.setName("蜜罐报告");
        this.setCost(MG_COST);
        addData();
        cost();
    }

    @Override
    public void addData() {
        JSONObject data = this.getData();
        data.put("name", "蜜罐报告");
        data.put("basic", "基础字段");
        this.setData(data);
    }

    @Override
    public void cost() {
        this.setCost(MG_COST);
    }
}
