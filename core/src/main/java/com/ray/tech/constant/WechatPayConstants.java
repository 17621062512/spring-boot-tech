package com.ray.tech.constant;

/**
 * 常量
 * 带#的为微信支付的必要参数
 */
public class WechatPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    /**
     * 微信支付国内域
     */
    public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    //    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    /**
     * 微信支付香港域
     */
    //    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    /**
     * 微信支付美国域
     */
    //    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


    public static final String FAIL = "FAIL";
    public static final String SUCCESS = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String MICROPAY_URL_SUFFIX = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

    //    #商户号
    public static final String APPID = "wx6ecc9d4d5d9b3c98";
    //    #商户平台设置的密钥key
    public static final String KEY = "8jUdihTkdsQ1R6POzgbf1Ps7AfDHJOF3";
    //    #商户号
    public static final String MCH_ID = "1325547301";
    //    #PC网页或公众号内支付，device_info固定为WEB
    public static final String DEVICE_INFO = "WEB";
    //e.g 腾讯充值中心-QQ会员充值
    //e.g 董乐乐点歌中心-付费点歌
//    public static final String BODY = "MusicConnect-付费点歌";
    public static final String BODY = "-付费点歌";
    //e.g 深圳分店 上海分店
    public static final String ATTACH = "上海总部";
    //    #支付的货币类型
    public static final String FEE_TYPE = "CNY";
    //    #下单方式，JSAPI为公众号
    public static final String TRADE_TYPE_JSAPI = "JSAPI";
    //    #下单方式，NATIVE为本地二维码
    public static final String TRADE_TYPE_NATIVE = "NATIVE";
    //    #no_credit--指定不能使用信用卡支付
    public static final String LIMIT_PAY = "no_credit";
    //    #给微信的回调接口,用于接受支付结果通知
    public static final String NOTIFY_URL = "https://www.abc.com/wechcat/order_callback";
    //自定义：订单有效时长，单位-分钟
    public static final int EXPIRY_TIME = 10;


}

