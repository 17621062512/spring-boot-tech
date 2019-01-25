package com.ray.tech.utils;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class Base64Utils {

    public static String stringToBase64(String image) {
        return String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64(image.getBytes()));// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param path 图片路径
     * @return base64字符串
     */
    public static String imageToBase64(String path) throws IOException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data;
        // 读取图片字节数组
        InputStream in = null;
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 处理Base64解码并写图片到指定位置
     *
     * @param base64 图片Base64数据
     * @param path   图片保存路径
     * @return
     */
    public static boolean base64ToImageFile(String base64, String path, String fileName) {// 对字节数组字符串进行Base64解码并生成图片
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File dest = new File(folder, fileName);
        BASE64Decoder decoder = new BASE64Decoder();
        FileOutputStream write = null;
        try {
            write = new FileOutputStream(dest);
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(base64);
            write.write(bytes);
        } catch (IOException e) {
            return false;
        } finally {
            if (write != null) {
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 处理Base64解码并输出流
     *
     * @param base64
     * @param out
     * @return
     */
    public static boolean base64ToImageOutput(String base64, OutputStream out) throws IOException {
        if (base64 == null) { // 图像数据为空
            return false;
        }
        try {
            // Base64解码
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64.getBytes());
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out.write(bytes);
            out.flush();
            return true;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否为Base64编码的图片
     *
     * @param base64Str
     * @return
     */
    public static boolean isImageFromBase64(String base64Str) {
        boolean flag = false;
        try {
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(base64Str)));
            if (null == bufImg) {
                return false;
            }
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return flag;
    }

}

