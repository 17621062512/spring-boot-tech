package com.ray.tech.constant;

import com.ray.tech.utils.FileUtils;

/**
 * 文件路径，类型常量
 */
public class FileConstant {
    public static final String UPLOAD_PATH = FileUtils.getProjectPath() + "\\.input\\upload\\";
    public static final String STATISTIC_CSV_PATH = FileUtils.getProjectPath() + "\\.output\\statistic_data_csv\\";

    public class Suffix {
        public static final String CSV = ".csv";
        public static final String JPG = ".jpg";
        public static final String JPEG = ".jpeg";
    }
}
