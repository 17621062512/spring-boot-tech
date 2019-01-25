package com.ray.tech.model;

import lombok.Data;

/**
 * Created by jacobdong on 15/7/16.
 */
@Data
public class ImageCaptcha {

    /**
     * 图片base64字符串
     */
    private String base64Img;

    /**
     * 令牌（由验证码 和 当前时间共同生成）
     */
    private String token;

    public ImageCaptcha() {
        //保留空构造
    }

    public ImageCaptcha(String base64Img, String token) {
        this.base64Img = base64Img;
        this.token = token;
    }
}
