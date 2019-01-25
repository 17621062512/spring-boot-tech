package com.ray.tech.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;

public class ImageCaptchaUtils {
    /**
     * 图片的宽度
     */
    private int width = 160;
    /**
     * 图片的高度
     */
    private int height = 40;
    /**
     * 验证码字符个数
     */
    private int codeCount = 5;
    /**
     * 验证码干扰线数
     */
    private int lineCount = 150;
    /**
     * 验证码
     */
    private String code = null;
    /**
     * 验证码图片Buffer
     */
    private BufferedImage buffImg = null;

    /**
     * 去除 数字 0,1 字母 O,L,O,I
     */
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};

    public ImageCaptchaUtils() {
        this.createCode();
    }

    /**
     * @param width  图片宽
     * @param height 图片高
     */
    public ImageCaptchaUtils(int width, int height) {
        this.width = width;
        this.height = height;
        this.createCode();
    }

    /**
     * @param width     图片宽
     * @param height    图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public ImageCaptchaUtils(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.createCode();
    }

    public void createCode() {
        int x, fontHeight, codeY;
        int red, green, blue;
        //每个字符的宽度
        x = width / (codeCount + 2);
        //字体的高度
        fontHeight = height - 2;
        codeY = height - 4;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体
        //ImgFontByte imgFont = new ImgFontByte();
        Font font = new Font("Arial", Font.PLAIN, fontHeight);
        g.setFont(font);
        //创建干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuilder randomCode = new StringBuilder();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        code = randomCode.toString();
    }

//    public void write(String path) throws IOException {
//        OutputStream sos = new FileOutputStream(path);
//        this.write(sos);
//    }

    private void write(OutputStream outputStream) throws IOException {
        ImageIO.write(buffImg, "png", outputStream);
        outputStream.close();
    }

    public String getbase64EncoderCaptcha() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.write(out);
        return new String(Base64.getEncoder().encode(out.toByteArray()));
    }


    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code;
    }

}
