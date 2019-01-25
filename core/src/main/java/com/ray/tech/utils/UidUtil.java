package com.ray.tech.utils;

import java.util.UUID;
public class UidUtil {
    public static String getUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
