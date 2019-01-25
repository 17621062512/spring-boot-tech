package com.ray.tech.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class MathStatisticUtil {
    /**
     * 皮尔逊相关系数相似度
     * 经过处理显示为百分比
     *
     * @param one
     * @param two
     */
    public static double calSimilarity(Map<String, Object> one, Map<String, Object> two) {
        // 找出双方都评论过的电影,（皮尔逊算法要求）
        List<String> list = new ArrayList<>();
        for (String key : one.keySet()) {
            if (two.containsKey(key)) {
                list.add(key);
            }
        }
        if (list.isEmpty()) {
            return 0.0;
        }
        double sumX = 0.0;
        double sumY = 0.0;
        double sumX_Sq = 0.0;
        double sumY_Sq = 0.0;
        double sumXY = 0.0;
        int N = list.size();
        for (String key : list) {
            Double value1 = Double.valueOf(one.get(key).toString());
            Double value2 = Double.valueOf(two.get(key).toString());

            sumX += value1;
            sumY += value2;
            sumX_Sq += Math.pow(value1, 2);
            sumY_Sq += Math.pow(value2, 2);
            sumXY += value1 * value2;
        }

        double numerator = sumXY - sumX * sumY / N;
        double a = (sumX_Sq - sumX * sumX / N) * (sumY_Sq - sumY * sumY / N);
        double temp = 1.0;
        if (a < 0) {
            temp = -1.0;
        }
        double denominator = Math.sqrt(Math.abs(a)) * temp;

        // 分母不能为0
        if (denominator == 0) {
            return 1.0;
        }

        return (((numerator / denominator) + 1.0) / 2.0);
    }

    public static double avg(Map<String, Double> map) {
        AtomicReference<Integer> times = new AtomicReference<>(0);
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        map.forEach((o, o2) -> {
            times.getAndSet(times.get() + 1);
            total.updateAndGet(v -> v + o2);
        });
        Integer integer = times.get();
        if (integer == 0) {
            return 0.0;
        } else {
            return total.get() / integer;
        }
    }

    private MathStatisticUtil() {
    }
}
