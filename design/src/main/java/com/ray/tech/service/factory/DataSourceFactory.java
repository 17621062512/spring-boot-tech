package com.ray.tech.service.factory;

import com.ray.tech.service.factory.store.JDDataSource;
import com.ray.tech.service.factory.store.MiguanDataSource;
import com.ray.tech.service.factory.store.ReportDataSource;
import com.ray.tech.service.factory.store.TaobaoDataSource;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceFactory {
    private static Map<String, Class<? extends DataSource>> dataSourceMap = new ConcurrentHashMap<>();

    static {
        dataSourceMap.put("报告", ReportDataSource.class);
        dataSourceMap.put("蜜罐", MiguanDataSource.class);
    }

    public static DataSource getDataSource(String type) {
        try {
            return dataSourceMap.get(type).newInstance();
        } catch (Exception e) {
            return ArrayList::new;
        }
    }

    public static void register(String type) throws NoSuchDataSourceException {
        dataSourceMap.putIfAbsent(type, getDataSourceClassByType(type));
    }

    public static void unregister(String type) {
        dataSourceMap.remove(type);
    }

    private static Class<? extends DataSource> getDataSourceClassByType(String type) throws NoSuchDataSourceException {
        switch (type) {
            case "报告":
                return ReportDataSource.class;
            case "蜜罐":
                return MiguanDataSource.class;
            case "京东":
                return JDDataSource.class;
            case "淘宝":
                return TaobaoDataSource.class;
            default:
                throw new NoSuchDataSourceException("不存在该类型的数据源");
        }
    }
}
