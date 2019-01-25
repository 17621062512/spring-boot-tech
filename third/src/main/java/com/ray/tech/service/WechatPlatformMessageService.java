package com.ray.tech.service;

import com.alibaba.fastjson.JSONObject;


import com.ray.tech.constant.SystemConstant;
import com.ray.tech.model.Article;
import com.ray.tech.model.Merchant;
import com.ray.tech.model.SubscribeUser;
import com.ray.tech.utils.WechatMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jacobdong on 2017/3/23.
 */
@Service
@Slf4j
public class WechatPlatformMessageService {


    @Resource
    private SubscribeUserService subscribeUserServiceBean;
    @Resource
    private MerchantService merchantServiceBean;

    private static SubscribeUserService subscribeUserService;
    private static MerchantService merchantService;


    @PostConstruct
    public void init() {
        subscribeUserService = subscribeUserServiceBean;
        merchantService = merchantServiceBean;
    }

    /**
     * example
     * {
     * "CreateTime": "1490252355",
     * "EventKey": "BTN_FAULT_LIST",
     * "Event": "CLICK",
     * "ToUserName": "gh_d1817b780d03",
     * "FromUserName": "opXjD0kiK5V1Goq4jUln63ah0zR8",
     * "MsgType": "event"
     * }
     *
     * @param request
     */
    public static String messageHandle(HttpServletRequest request) {

        try {
            Map originMessage = WechatMessageUtil.parseXml(request);
            log.info(JSONObject.toJSONString(originMessage));
            String messageType = (String) originMessage.get("MsgType");

            switch (messageType) {
                case "event":
                    return eventHandle(originMessage);
                default:
                    return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "success";
        }
    }

    private static String eventHandle(Map originMessage) {
        String event = (String) originMessage.get("Event");
        String eventId = (String) originMessage.get("EventKey");
        String senderId = (String) originMessage.get("ToUserName");
        String openId = (String) originMessage.get("FromUserName");

        String eventKey = (String) originMessage.get("EventKey");
        String merchantId = "";
        String[] split = {"", "", "", ""};
        if (null != eventKey && !eventKey.contains("qrscene")) {
            eventKey = "qrscene_" + eventKey;
        }
        split = eventKey.split("_");
        merchantId = split[3];

        switch (event) {
            case "unsubscribe":
                log.warn(openId + "取消关注了");
                subscribeUserService.unsubscribe(subscribeUserService.findByOpenId(openId));
                return "success";
            case "subscribe":
                SubscribeUser subscribeUser = subscribeUserService.findByOpenId(openId);
                if (null == subscribeUser) {
                    subscribeUser = new SubscribeUser();
                    subscribeUser.setOpenId(openId);
                }
                subscribeUser.setSubscribe(true);
                subscribeUserService.subscribe(subscribeUser);
                subscribeUserService.recordOperations(openId, merchantId);
                if (StringUtils.isNotEmpty(eventKey)) {
                    return sendQrcodeScanMessage(openId, senderId, eventKey);
                } else {
                    return getTextMessage(openId, senderId, "欢迎关注！");
                }
            case "SCAN":
                subscribeUserService.recordOperations(openId, merchantId);
                return sendQrcodeScanMessage(openId, senderId, eventKey);
            default:
                switch (eventId) {
                    //自定义事件的key
                    case "BTN_FAULT_LIST":
                    case "BTN_MOBILE_LIST":
                        return getTextMessage(openId, senderId, "");
                    case "BTN_HOUSING_FUND_LIST":
                        return getTextMessage(openId, senderId, "");
                    case "BTN_SOCIAL_INSURANCE_LIST":
                        return getTextMessage(openId, senderId, "");
                    case "UNKNOWN":
                        //TODO 自定义事件处理
                        String faultMessage = getNotificationContent();
                        return getTextMessage(openId, senderId, faultMessage);
                    default:
                        return getTextMessage(openId, senderId, "对不起，此功能暂未开放");
                }
        }
    }

    /**
     * 发送图文消息
     *
     * @param toOpenId
     * @param senderId
     * @return
     */
    private static String getMenuInfoMessage(String toOpenId, String senderId, String merchantId) {
        Merchant merchant = merchantService.findBy(merchantId);
        List<Article> articles = new ArrayList<>();
        Article article4Order = new Article();
        article4Order.setTitle("欢迎光临[" + merchant.getName() + "], 戳我点歌!!!（Press Me!!!）");
        article4Order.setDescription("欢迎光临" + merchant.getName() + "，本店提供音乐点播服务");
        article4Order.setUrl(SystemConstant.SYSTEM_OAUTH + "?merchant_id=" + merchantId + "&openid=" + toOpenId);
        article4Order.setPicUrl("https://mla.ray.com/image/cover-guide.jpg");
        articles.add(article4Order);

        Article articlePlayingList = new Article();
        articlePlayingList.setTitle(" 待播列表 Pending List");
        articlePlayingList.setDescription("尽情期待");
        articlePlayingList.setUrl(SystemConstant.PAGE_WAITING_LIST_URL + "?merchant_id=" + merchantId + "&openid=" + toOpenId);
        articlePlayingList.setPicUrl("https://mla.ray.com/image/music-queue-1.jpg");
        articles.add(articlePlayingList);

//        Article articleFriend = new Article();
//        articleFriend.setTitle("\uD83C\uDD92 附近的人（Nearbys）");
//        articleFriend.setDescription("尽情期待");
//        articleFriend.setUrl(SystemConstant.SYSTEM_OAUTH + "?merchant_id=" + merchantId);
//        articleFriend.setPicUrl("https://mla.ray.com/image/soon.jpg");
//        articles.add(articleFriend);

        Article articleTop = new Article();
        articleTop.setTitle(" 本店榜单 Customer Ranking");
        articleTop.setDescription("尽情期待");
        articleTop.setUrl(SystemConstant.PAGE_MUSIC_STATISTIC_USER_RANK_URL + "?merchant_id=" + merchantId + "&openid=" + toOpenId);
        articleTop.setPicUrl("https://mla.ray.com/image/music-ranking.jpg");
        articles.add(articleTop);
        for (Article article : articles) {
            log.info(article.getUrl());
        }
        return getArticlesMessage(toOpenId, senderId, articles);
    }

    private static String sendQrcodeScanMessage(String toOpenId, String senderId, String eventKey) {
        //e.g.  qrscene_MERCHAT_SUBSCRIBE_001
        if (!eventKey.contains("qrscene")) {
            eventKey = "qrscene_" + eventKey;
        }
        String[] split = eventKey.split("_");
//        String type = split[1];
//        String action = split[2];
        String merchantId = split[3];
        return getMenuInfoMessage(toOpenId, senderId, merchantId);
    }

    private static String getTextMessage(String openId, String senderId, String context) {
        String template = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>[CreateTime]</CreateTime><MsgType><![CDATA[MsgType]]></MsgType><Content><![CDATA[Content]]></Content></xml>";
        template = template.replace("[toUser]", addSuffix(openId));
        template = template.replace("[fromUser]", addSuffix(senderId));
        template = template.replace("[CreateTime]", String.valueOf(System.currentTimeMillis()));
        template = template.replace("[MsgType]", addSuffix("text"));
        template = template.replace("[Content]", addSuffix(context));
        return template;
    }

    public static String getArticlesMessage(String toOpenId, String senderId, List<Article> articles) {
        String template = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>[CreateTime]</CreateTime><MsgType><![CDATA[MsgType]]></MsgType><ArticleCount>[ArticleCount]</ArticleCount><Articles>";
        template = template.replace("[toUser]", addSuffix(toOpenId));
        template = template.replace("[fromUser]", addSuffix(senderId));
        template = template.replace("[CreateTime]", String.valueOf(System.currentTimeMillis()));
        template = template.replace("[ArticleCount]", String.valueOf(articles.size()));
        template = template.replace("[MsgType]", addSuffix("news"));
        String content = getArticleItemMessage(articles);
        template += content;
        template += "</Articles></xml>";
        return template;
    }

    private static String getArticleItemMessage(List<Article> articles) {
        StringBuilder stringBuffer = new StringBuilder();
        for (Article article : articles) {
            String template = "<item><Title><![CDATA[Title]]></Title> " +
                    "<Description><![CDATA[Description]]></Description><PicUrl><![CDATA[PicUrl]]></PicUrl><Url><![CDATA[Url]]></Url></item>";
            template = template.replace("[Title]", addSuffix(article.getTitle()));
            template = template.replace("[Description]", addSuffix(article.getDescription()));
            template = template.replace("[PicUrl]", addSuffix(article.getPicUrl()));
            template = template.replace("[Url]", addSuffix(article.getUrl()));
            log.info("添加一个图文消息");
            stringBuffer.append(template);
        }
        return stringBuffer.toString();
    }

    private static String addSuffix(String field) {
        return "[" + field + "]";
    }

    private static String getNotificationContent() {
        return "按钮未设置";
    }
}
