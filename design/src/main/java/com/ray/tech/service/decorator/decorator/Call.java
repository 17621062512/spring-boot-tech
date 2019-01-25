package com.ray.tech.service.decorator.decorator;

import com.ray.tech.service.decorator.Report;
import com.ray.tech.service.decorator.ReportDecorator;

import java.util.HashMap;
import java.util.Map;

public class Call extends ReportDecorator {
    private static final Map<String, Object> CALL_DATA = new HashMap<>();
    private static final double COST = 0.8;
    private Report report;

    static {
        CALL_DATA.put("call", "通话信息");
    }

    public Call(Report report) {
        this.report = report;
        addData();
        cost();
    }

    @Override
    public void addData() {
        report.getData().fluentPutAll(CALL_DATA);
    }

    @Override
    public void cost() {
        report.setCost(report.getCost() + COST);
    }
}
