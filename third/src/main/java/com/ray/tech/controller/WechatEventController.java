package com.ray.tech.controller;

import com.ray.tech.constant.WechatPlatformURL;
import com.ray.tech.utils.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import com.ray.tech.service.WechatPlatformMessageService;

@Slf4j
@RequestMapping(value = "/mp/receiver")
@RestController
public class WechatEventController {

    @GetMapping
    public String pushNotification(@RequestParam(name = "signature", required = false) String signature,
                                   @RequestParam(name = "nonce", required = false) String nonce,
                                   @RequestParam(name = "timestamp", required = false) String timestamp,
                                   @RequestParam(name = "echostr", required = false) String echostr) {


        try {
            log.info("signature:" + signature);
            log.info("nonce:" + nonce);
            log.info("timestamp:" + timestamp);
            log.info("echostr:" + echostr);
            String haxStr = EncryptUtils.getSHA1(WechatPlatformURL.TOKEN, timestamp, nonce);
            log.info("hexStr:" + haxStr);

            if (signature.equals(haxStr)) {
                log.info("微信接入成功");
                return echostr;
            } else {
                log.info("微信接入失败");
                return "";
            }
        } catch (Exception e) {
            log.info("调用加密失败");
            e.printStackTrace();
            return "";
        }

    }


    /**
     * 调用核心业务类接收消息、处理消息跟推送消息
     *
     * @param request
     * @return
     */
    @PostMapping
    public String post(HttpServletRequest request) {
        try {
            return WechatPlatformMessageService.messageHandle(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析接收到的消息失败");
            return "success";
        }

    }

}
