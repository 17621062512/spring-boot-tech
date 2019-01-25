package com.ray.tech.service.adapter;

public  class CustomerAdapter {
    private JuXinLiClazz myClazz = new JuXinLiClazz();

    public Integer getJXLData() {
        return Integer.valueOf(myClazz.getSomeData());
    }
}
