package com.ray.tech.service.decorator.report;

import com.alibaba.fastjson.JSONObject;
import com.ray.tech.service.decorator.Report;

public class MiFengReport extends Report {
    private static final double MF_COST = 1.3;

    public MiFengReport() {
        this.setName("蜜蜂报告");
        this.setCost(MF_COST);
        addData();
        cost();
    }

    @Override
    public void addData() {
        JSONObject data = this.getData();
        data.put("name", "蜜蜂报告");
        data.put("basic", "基础字段");
        this.setData(data);
    }

    @Override
    public void cost() {
        this.setCost(MF_COST);
    }
}
