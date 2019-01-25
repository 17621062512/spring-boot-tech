package com.ray.tech.utils;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class CollectionUtils {
    /**
     * 根据对象的一个指定Field\去重
     *
     * @param list
     * @param field
     * @param <T>
     * @param <U>
     * @return ArrayList
     */
    public static <T, U extends Comparable<? super U>> List<T> distinctByField(List list, Function<? super T, ? extends U> field) {
        return (List<T>) list.stream().filter(Objects::nonNull).collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(field))), ArrayList::new));

    }
}
