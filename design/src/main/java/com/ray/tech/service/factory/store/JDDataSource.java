package com.ray.tech.service.factory.store;

import com.ray.tech.service.factory.DataSource;

import java.util.ArrayList;
import java.util.List;

public class JDDataSource implements DataSource {
    private static List<String> data = new ArrayList<>();

    static {
        data.add("京东1");
        data.add("京东2");
        data.add("京东3");
        data.add("京东4");
        data.add("京东5");
        data.add("京东6");
        data.add("京东7");
        data.add("京东8");
        data.add("京东9");
    }

    @Override
    public List getData() {
        return data;
    }
}
