package com.ray.tech.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileUtils {
    public static synchronized void write(File file, String data, boolean append) throws IOException {

        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), "GBK"));
            bw.write(data + "\r\n");
            bw.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    /**
     * 读取静态资源
     *
     * @param path  相对路径
     * @param clazz 获取ClassLoader
     * @return 文件内容
     */
    public static synchronized String readClassPathFile(String path, Class clazz) {
        StringBuilder builder = new StringBuilder();
        InputStreamReader read = null;
        try {
            InputStream inputStream = clazz.getResourceAsStream(path);
            read = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String txt;
            while ((txt = bufferedReader.readLine()) != null) {
                builder.append(txt);
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    /**
     * 获取项目根目录
     *
     * @return .../project-name
     */
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    /**
     * Spring Resource 实时读取文件
     *
     * @param path 绝对路径
     */
    public static List<String> readLine(String path) throws IOException {
        BufferedReader br = null;
        ArrayList<String> strings = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile(path);
            br = new BufferedReader(new FileReader(file));
            String temp = null;
            do {
                temp = br.readLine();
                if (temp != null) {
                    strings.add(temp);
                }
            } while (temp != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
            return strings;
        }

    }
}
