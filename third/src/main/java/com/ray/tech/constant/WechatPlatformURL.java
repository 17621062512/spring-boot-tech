package com.ray.tech.constant;

public class WechatPlatformURL {
    /**
     * 微信小程序
     */
    private static final String MINI_APP_ID = "1";
    private static final String MINI_APP_SECRET = "2";
    public static final String MINI_APP_GEN_AUTHORIZATION_CODE_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&appid=" + MINI_APP_ID + "&secret=" + MINI_APP_SECRET + "&js_code={code}";
    /**
     * 聚会小程序
     */
    private static final String MINI_APP_ID_PARTY = "3";
    private static final String MINI_APP_SECRET_PARTY = "4";
    public static final String MINI_APP_GEN_AUTHORIZATION_CODE_ACCESS_TOKEN_PARTY = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&appid=" + MINI_APP_ID_PARTY + "&secret=" + MINI_APP_SECRET_PARTY + "&js_code={code}";

    /**
     * music_connect公众号
     */
    public static final String TOKEN = "mp_token";
    private static final String APP_ID = "5";
    private static final String SECRET = "6";

    public static final String GEN_AUTHORIZATION_CODE_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=" + APP_ID + "&secret=" + SECRET + "&code={code}";
    public static final String GEN_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + SECRET;
    public static final String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APP_ID + "&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
    public static final String ENCRYPT_STR = "666";
    public static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token={access_token}&openid={openid}&lang=zh_CN";
    public static final String GET_SUBSCRIBE_OPENIDS = "https://api.weixin.qq.com/cgi-bin/user/get?access_token={access_token}";
    public static final String PUSH_MESSAGE_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={access_token}";
    /**
     * 带参二维码生成请求post
     * 临时：
     * {"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}  id必须为整型
     * {"expire_seconds": 604800, "action_name": "QR_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
     * <p>
     * 永久（限量10万）：
     * {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
     * {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
     * <p>
     * action_name:     * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
     * scene_str:       场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * scene_id:        场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     */
    public static final String QRCODE_CREATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={access_token}";
    /**
     * 通过生成的ticket获取二维码图片
     */
    public static final String QRCODE_GET = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket={TICKET}";
}
