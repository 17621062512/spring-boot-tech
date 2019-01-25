package com.ray.tech.service.factory.store;

import com.ray.tech.service.factory.DataSource;

import java.util.ArrayList;
import java.util.List;

public class TaobaoDataSource implements DataSource {
    private static List<String> data = new ArrayList<>();

    static {
        data.add("淘宝1");
        data.add("淘宝2");
        data.add("淘宝3");
        data.add("淘宝4");
        data.add("淘宝5");
        data.add("淘宝6");
        data.add("淘宝7");
        data.add("淘宝8");
        data.add("淘宝9");
    }

    @Override
    public List getData() {
        return data;
    }
}
