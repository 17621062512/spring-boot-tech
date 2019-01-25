package com.ray.tech.service.factory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    public List getData(String type) {
        return DataSourceFactory.getDataSource(type).getData();
    }

    public void register(String type) throws NoSuchDataSourceException {
        DataSourceFactory.register(type);
    }

    public void unregister(String type) {
        DataSourceFactory.unregister(type);
    }
}
