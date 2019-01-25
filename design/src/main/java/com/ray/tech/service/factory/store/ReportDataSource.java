package com.ray.tech.service.factory.store;

import com.ray.tech.service.factory.DataSource;

import java.util.ArrayList;
import java.util.List;

public class ReportDataSource implements DataSource {
    private static List<String> data = new ArrayList<>();

    static {
        data.add("报告1");
        data.add("报告2");
        data.add("报告3");
        data.add("报告4");
        data.add("报告5");
        data.add("报告6");
        data.add("报告7");
        data.add("报告8");
        data.add("报告9");
    }

    @Override
    public List getData() {
        return data;
    }


}
