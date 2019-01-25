package com.ray.tech.constant;

import com.ray.tech.utils.WechatMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

@Slf4j
public class WechatPayApiURL {
    /**
     * 微信统一下单接口
     */
    public static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信支付状态查询接口
     */
    public static final String QUERY_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     * 微信日账单下载接口
     */
    public static final String DOWNLOAD_BILL = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /**
     * 生成统一下单接口的基本数据
     *
     * @param out_trade_no     本公司订单号
     * @param spbill_create_ip 用户IP
     * @param total_fee        总价
     * @param openid           openid
     * @param time_start       订单发起时间
     * @param time_expire      订单失效时间
     * @param merchantName     商铺名
     * @return 基本数据
     */
    public static HashMap<String, String> getUnifiedOrderRequestArgs(String out_trade_no, String spbill_create_ip,
                                                                     String total_fee, String openid,
                                                                     String time_start, String time_expire, String merchantName, String tradeType) {

        HashMap<String, String> baseData = new HashMap<>();
        baseData.put("body", (merchantName + WechatPayConstants.BODY));
        baseData.put("device_info", WechatPayConstants.DEVICE_INFO);
        baseData.put("fee_type", WechatPayConstants.FEE_TYPE);
        baseData.put("notify_url", WechatPayConstants.NOTIFY_URL);
        baseData.put("appid", WechatPayConstants.APPID);
        baseData.put("mch_id", WechatPayConstants.MCH_ID);
        baseData.put("nonce_str", WechatMessageUtil.geneNonceStr());
        baseData.put("attach", WechatPayConstants.ATTACH);
        baseData.put("limit_pay", WechatPayConstants.LIMIT_PAY);
        baseData.put("trade_type", tradeType);
//        if (tradeType.equals(WechatPayConstants.TRADE_TYPE_NATIVE)) {
//            baseData.put("product_id", "1");
//        }
        baseData.put("out_trade_no", out_trade_no);
        baseData.put("spbill_create_ip", spbill_create_ip);
        baseData.put("total_fee", total_fee);
        if (StringUtils.isNotEmpty(openid)) {
            baseData.put("openid", openid);
        }
        baseData.put("time_start", time_start);
        baseData.put("time_expire", time_expire);
        log.info("微信预下单请求参数Map：" + baseData.toString());
        return baseData;
    }

    public static HashMap<String, String> getOrderQueryRequestArgs(String out_trade_no) {
        HashMap<String, String> baseData = new HashMap<>();
        baseData.put("appid", WechatPayConstants.APPID);
        baseData.put("mch_id", WechatPayConstants.MCH_ID);
        baseData.put("out_trade_no", out_trade_no);
        baseData.put("nonce_str", WechatMessageUtil.geneNonceStr());
        return baseData;
    }

    /**
     * bill_date  下载日账单日期，格式 20170101 ，只能下载3个月内的日账单
     *
     * @param billDate
     * @return
     */
    public static HashMap<String, String> getDownloadBillArgs(String billDate) {
        HashMap<String, String> baseData = new HashMap<>();
        baseData.put("appid", WechatPayConstants.APPID);
        baseData.put("mch_id", WechatPayConstants.MCH_ID);
        baseData.put("nonce_str", WechatMessageUtil.geneNonceStr());
        baseData.put("bill_date", billDate);
        baseData.put("bill_type", "ALL");
        return baseData;
    }
}
