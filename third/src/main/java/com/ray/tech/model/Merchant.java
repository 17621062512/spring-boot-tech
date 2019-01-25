package com.ray.tech.model;

import com.ray.tech.utils.UidUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 商铺
 */
@Data
@Document(collection = "merchant")
public class Merchant {
    /**
     * 商铺ID
     */
    @Id
    private String id = UidUtil.getUid();
    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 是否可以留言
     */
    private Boolean acceptMsg;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 商铺歌曲定价(单位=分)
     */
    private Integer fee;

    /**
     * 二维码url
     */
    private String qrUrl;

    /**
     * 歌曲黑名单
     */
    private Set<String> musicBlackList = new HashSet<>();
//    /**
//     * 营业日
//     */
//    private int[] openDay = {1, 2, 3, 4, 5, 6, 7};
    /**
     * 营业时间段
     */
    private List<Long[]> openTime;
    /**
     * 最近的一次拉取时间
     */
    private Long lastPopTime;
    /**
     * 上次登录时间
     */
    private Long signInTime;
    /**
     * 语言偏好
     */
    private PreferenceType preferenceType;
    /**
     * 优惠率
     */
//    @Min(0)
    private Double takeOff;
    /**
     * 优惠次数
     */
    private Integer discountTimes;
    /**
     * 播放设备
     */
    private String device;
    /**
     * 总收入
     */
    private Integer totalIncome;
    /**
     * 余额
     */
    private Integer balance;
    /**
     * 提成率
     */
    private Long extractionRate = 0L;

    /**
     * 添加收入
     *
     * @param fee
     */
    public void add2TotalIncome(Integer fee) {
        if (this.extractionRate == null) {
            this.extractionRate = 0L;
        }
        Integer actualIncome = Math.toIntExact(fee * this.extractionRate);
        if (this.totalIncome == null) this.totalIncome = 0;
        if (this.balance == null) this.balance = 0;
        this.totalIncome += actualIncome;
        this.balance += actualIncome;
    }

    /**
     * 提现
     *
     * @param takeCount
     */
    public void withdrawal(Integer takeCount) {
        if (this.balance == null) {
            this.balance = 0;
        }
        this.balance -= takeCount;
    }

    public enum PreferenceType {
        /**
         *
         */
        ALL("该商铺可以播放所有歌曲"),
        /**
         *
         */
        CHINESE("很抱歉，该商铺仅限点播中文歌曲"),
        /**
         *
         */
        ENGLISH("很抱歉，该商铺仅限点播英文歌曲");

        String msg;

        PreferenceType() {
        }

        PreferenceType(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }


}
