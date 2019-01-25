package com.ray.tech.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

@Slf4j
public class GlobalUtils {
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final SimpleDateFormat TASK_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);

    public static final SimpleDateFormat SECOND_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static String getUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
