package com.ray.tech.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 订单
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "pending_music")
public class PendingMusic extends Music {
    /**
     * 订单ID
     */
    @Id
    private String id = geneOrderId();
    /**
     * 商户ID
     */
    private String merchantId;
    /**
     * 订单创建时间
     */
    private long createTime;
    /**
     * 是否被消费(已被前端拿走)
     */
    private boolean consumed;
    /**
     * 微信用户的openid
     */
    private String userOpenId;
    /**
     * 点歌人
     */
    private String userName;
    /**
     * 留言,等
     */
    private String message;
//    /**
//     * 预支付订单号(统一下单接口返回)
//     */
//    private String prepayId;
    /**
     * 订单状态
     */
    private BillStatus status;
    /**
     * 是否已经通知微信(由回调接口通知)
     */
    private Boolean isSent = false;
    /**
     * 金额
     */
    private long fee;
//    /**
//     * 支付银行
//     */
//    private String bank;
//    /**
//     * 微信支付订单号
//     */
//    private String wxBillId;
    /**
     * 支付完成时间(微信支付必备)
     */
    private String timeEnd;
    /**
     * 最新操作时间
     */
    private String newestUpdTime;
    //    /**
//     * 订单发起时间(微信支付必备)
//     */
//    private String timeStart;
//    /**
//     * 订单失效时间(微信支付必备)
//     */
//    private String timeExpire;
    /**
     * 额外参数
     */
    private String attach;
    private long popTime;
    private long finishTime;

    //生成本公司订单
    private static String geneOrderId() {
        String date = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).replaceAll(":", "").split("\\.")[0];
        return date + time + RandomStringUtils.randomAlphanumeric(10).toUpperCase();
    }

    public enum BillStatus {
        NOT_PAY,//下单成功，但未付款  usage in OrderService.getWechatPayReturn()统一下单
        CLOSED,//关单
        PAY_ERROR,//支付错误
        REFUND,//退款
        FAILED,//下单失败   usage in OrderService.getWechatPayReturn()统一下单（默认）
        PAY_SUCCESS//支付成功
    }
}
