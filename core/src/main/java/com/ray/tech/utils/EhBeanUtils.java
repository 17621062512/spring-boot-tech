package com.ray.tech.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * create by EndEcho
 */
public class EhBeanUtils {
    /**
     * 将空值的属性从目标实体类中复制到源实体类中
     *
     * @param src    : 要将属性中的空值覆盖的对象(源实体类)
     * @param target :从数据库根据id查询出来的目标对象
     */
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullProperties(src));
    }

    public static void copyNonNullProperties(Object src, Object target, String... ignoreFields) {
        BeanUtils.copyProperties(src, target, getNullProperties(src, ignoreFields));
    }

    public static void copyIncludedNonNullProperties(Object src, Object target, String... copyFields) {
        BeanUtils.copyProperties(src, target, exceptIncludedFields(src, copyFields));
    }

    public static void copyProperties(Object src, Object target, String... ignoreFields) {
        BeanUtils.copyProperties(src, target, ignoreFields);
    }


    /**
     * 将为空的properties给找出来,然后返回出来
     *
     * @param src
     * @return
     */
    private static String[] getNullProperties(Object src, String... ignoreField) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> emptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {

            Object srcValue = srcBean.getPropertyValue(p.getName());
            if (srcValue == null) emptyName.add(p.getName());


        }
        if (ignoreField.length > 0) {
            emptyName.addAll(Arrays.asList(ignoreField));
        }
        String[] result = new String[emptyName.size()];
        return emptyName.toArray(result);
    }

    /**
     * 获取所有为空，和不在copyFields中的变量名
     *
     * @param src
     * @param copyFields
     * @return
     */
    private static String[] exceptIncludedFields(Object src, String... copyFields) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> ignoreNames = new HashSet<>();
        List<String> list = Arrays.asList(copyFields);
        for (PropertyDescriptor p : pds) {
            Class<?> propertyClass = p.getPropertyType();

            if (propertyClass != null && propertyClass.isMemberClass()) {
                Object value = srcBean.getPropertyValue(p.getName());
                if (value != null) {
                    Field[] declaredFields = value.getClass().getDeclaredFields();
                    List<String> c = Arrays.asList(exceptIncludedFields(value, copyFields));
                    if (c.size() == declaredFields.length) {
                        ignoreNames.add(p.getName());
                        ignoreNames.addAll(c);
                    } else {
                        ignoreNames.addAll(c);
                    }
                } else {
                    ignoreNames.add(p.getName());
                }
            } else {
                Object srcValue = srcBean.getPropertyValue(p.getName());
                if (srcValue == null || !list.contains(p.getName())) ignoreNames.add(p.getName());
            }
        }
        ignoreNames.remove("class");
        String[] result = new String[ignoreNames.size()];
        return ignoreNames.toArray(result);
    }

    /**
     * 检测所有字段不为空
     *
     * @param src         :要将属性中的空值覆盖的对象(源实体类)
     * @param ignoreField :忽略的字段名
     * @return Boolean
     */
    public static boolean isNonNulls(Object src, String... ignoreField) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> emptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            boolean jump = false;
            for (String name : ignoreField) {
                if (name.equalsIgnoreCase(p.getName())) {
                    jump = true;
                }
            }
            if (!jump) {
                Object srcValue = srcBean.getPropertyValue(p.getName());
                if (srcValue instanceof String) {
                    if (org.springframework.util.StringUtils.isEmpty(srcValue)) {
                        emptyName.add(p.getName());
                    }
                } else if (srcValue == null) {
                    emptyName.add(p.getName());
                }
            }
        }
        return emptyName.size() == 0;
    }

    private EhBeanUtils() {
    }
}
