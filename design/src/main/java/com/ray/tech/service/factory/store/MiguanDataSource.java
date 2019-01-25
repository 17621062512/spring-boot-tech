package com.ray.tech.service.factory.store;

import com.ray.tech.service.factory.DataSource;

import java.util.ArrayList;
import java.util.List;

public class MiguanDataSource implements DataSource {
    private static List<String> data = new ArrayList<>();

    static {
        data.add("蜜罐1");
        data.add("蜜罐2");
        data.add("蜜罐3");
        data.add("蜜罐4");
        data.add("蜜罐5");
        data.add("蜜罐6");
        data.add("蜜罐7");
        data.add("蜜罐8");
        data.add("蜜罐9");
    }

    @Override
    public List getData() {
        return data;
    }
}
