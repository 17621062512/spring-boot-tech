package com.ray.tech.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {

    /**
     * 黑色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 白色
     */
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 宽
     */
    private static final int WIDTH = 400;
    /**
     * 高
     */
    private static final int HEIGHT = 400;

    /**
     * 生成二维码并使用Base64编码
     *
     * @param content 二维码内容
     * @return 返回base64图片
     * @throws Exception
     */
    public static String getBase64QRCode(String content) throws Exception {
        String format = "png";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        @SuppressWarnings("rawtypes")
        Map hints = new HashMap();

        // 设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN, 1);
        // 设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 画二维码
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。
        ImageIO.write(image, format, os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        byte b[] = os.toByteArray();//从流中获取数据数组。
        // Base64编码
        return new BASE64Encoder().encode(b);
    }
}
