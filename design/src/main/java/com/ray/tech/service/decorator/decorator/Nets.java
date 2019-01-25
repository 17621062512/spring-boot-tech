package com.ray.tech.service.decorator.decorator;

import com.ray.tech.service.decorator.Report;
import com.ray.tech.service.decorator.ReportDecorator;

import java.util.HashMap;
import java.util.Map;

public class Nets extends ReportDecorator {
    private static final Map<String, Object> NETS_DATA = new HashMap<>();
    private static final double COST = 0.7;
    private Report report;

    static {
        NETS_DATA.put("nets", "流量信息");
    }

    public Nets(Report report) {
        this.report = report;
        addData();
        cost();
    }

    @Override
    public void addData() {
        report.getData().fluentPutAll(NETS_DATA);
    }

    @Override
    public void cost() {
        report.setCost(report.getCost() + COST);
    }
}
