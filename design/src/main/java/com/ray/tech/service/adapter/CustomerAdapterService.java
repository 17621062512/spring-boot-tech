package com.ray.tech.service.adapter;

public class CustomerAdapterService {
    public int showAdapter() {
        OtherClazz otherClazz = new OtherClazz();
        return otherClazz.weNeedSth(new CustomerAdapter().getJXLData());

    }
}
