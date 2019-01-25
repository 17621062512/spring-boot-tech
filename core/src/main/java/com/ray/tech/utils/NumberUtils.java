package com.ray.tech.utils;

import java.math.BigDecimal;

public class NumberUtils {
    public static double scaleDouble(int scale, Long number) {
        return scaleDouble(scale, number * 1.0);
    }

    public static double scaleDouble(int scale, Double number) {
        return new BigDecimal(number).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
