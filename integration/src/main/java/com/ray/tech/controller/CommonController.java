package com.ray.tech.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.ray.tech.config.CustomerConfig;
import com.ray.tech.constant.CommonConstant;
import com.ray.tech.exception.ArgumentException;
import com.ray.tech.exception.PermissionException;
import com..tech.model.*;
import com.ray.tech.model.*;
import com.ray.tech.service.EnterpriseService;
import com.ray.tech.utils.EncryptUtils;
import com.ray.tech.utils.GlobalUtils;
import com.ray.tech.utils.ImageCaptchaUtils;
import com.ray.tech.utils.PreconditionsUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author JacobDong
 * @date 2018/5/15 17:32
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    private CustomerConfig customerConfig;

    /**
     * 机构账号密码登录
     *
     * @param passport 登录时的必要信息
     * @return Auth Token
     * @throws IOException
     */
    @SentinelResource("/login")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody Passport passport) throws Exception {
        checkParams(passport);
        String account = passport.getAccount();
        String password = passport.getPassword();
        OrganizationBo organization = EnterpriseService.getOrganizationByAccountAndPassword(account, password);
        if (null == organization) {
            throw new PermissionException("机构账号密码不匹配");
        }
        if (organization.getStatus().equals(OrganizationStatus.NORMAL)) {
            return new ApiResponse<>(generateAuthorization(organization));
        } else if (organization.getStatus().equals(OrganizationStatus.FROZEN)) {
            throw new PermissionException("机构账号状态异常：已冻结");
        } else {
            throw new PermissionException("机构账号状态异常：已过期");
        }
    }

    /**
     * 生成图片验证码
     *
     * @return
     */
//    @SentinelResource("/captcha/image")
    @GetMapping("/captcha/image")
    public ApiResponse sendImage() throws InterruptedException {
        Entry entry = null;
        try {
            //持有资源
            entry = SphU.entry("/captcha/image");
            ImageCaptchaUtils captcha = new ImageCaptchaUtils(150, 40, 4, 40);
            String imgBase64 = captcha.getbase64EncoderCaptcha();
            String appKey = (captcha.getCode() + CommonConstant.KEY_SUFFIX).toLowerCase();
            String currentTime = String.valueOf(System.currentTimeMillis());
            String token = EncryptUtils.aesEncryptHexString(currentTime, appKey);
            log.info("[监控平台]<登录> 生成验证码成功:" + captcha.getCode());
            return new ApiResponse<>(new ImageCaptcha(imgBase64, token));
        } catch (BlockException e) {
            //资源不足时触发阻塞
            log.warn("触发限流", e);
            return new ApiResponse<>("系统繁忙请稍后再试");
        } catch (Exception e) {
            log.error("[监控平台]<登录> 验证码生成失败", e);
            return ApiResponse.createFeedback(ControllerFeedbackEnum.API_CAPTCHA_GET_FAIL);
        } finally {
            if (entry != null) {
                //释放资源
                entry.exit();
            }
        }
    }

    /**
     * 检查参数，图片验证码及其过期时间
     *
     * @param passport
     * @return
     */
    private static boolean checkParams(Passport passport) {
        PreconditionsUtil.checkArgument(StringUtils.isNotBlank(passport.getAccount()), "机构账号不能为空");
        PreconditionsUtil.checkArgument(StringUtils.isNotBlank(passport.getPassword()), "机构密码不能为空");
        PreconditionsUtil.checkArgument(StringUtils.isNotBlank(passport.getCaptcha()), "图片验证码不能为空");
        try {
            log.info("[监控平台]<登录> 图片验证码为" + passport.getCaptcha());
            String appKey = (passport.getCaptcha() + CommonConstant.KEY_SUFFIX).toLowerCase();
            String getCaptchaTime = EncryptUtils.aesDecryptHexString(passport.getToken(), appKey);
            long sendTime = Long.parseLong(getCaptchaTime);
            long currentTime = System.currentTimeMillis();
            log.debug("[监控平台]<登录> 图片验证码 发送:{} ==> 当前时间为{}", GlobalUtils.SECOND_DATE_FORMAT.format(new Date(sendTime)), GlobalUtils.SECOND_DATE_FORMAT.format(new Date(currentTime)));
            if ((currentTime - sendTime) > CommonConstant.CAPTCHA_TIMEOUT) {
                throw new ArgumentException("图片验证码已过期，请刷新验证码后重试");
            }
        } catch (Exception e) {
            throw new ArgumentException("图片验证码输入错误，请重新输入");
        }
        return true;
    }

    /**
     * 生成鉴权信息
     *
     * @param organization 机构信息
     * @return 鉴权对象
     */
    private Authorization generateAuthorization(OrganizationBo organization) {
        try {
            Authorization auth = new Authorization();
            String tokenEncryptKey = organization.getAccount() + CommonConstant.AUTHED_KEY_SUFFIX;
            String encryptApiKey = EncryptUtils.aesEncryptHexString(organization.getApiKey(), tokenEncryptKey);
            organization.setApiKey(encryptApiKey);
            Map map = new HashMap();
            map.put("report_version", organization.getReportVersion());
            map.put("name", organization.getName());
            auth.setInfo(map);
            auth.setSignInTime(System.currentTimeMillis());
            auth.setAuthorization(Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setClaims(JSON.parseObject(JSON.toJSONString(organization)))
                    .claim("type", Authorization.AuthType.ORIGANIZATION)
                    .setExpiration(new Date(System.currentTimeMillis() + CommonConstant.LOGIN_EXPIRATION))
                    .signWith(SignatureAlgorithm.HS512, CommonConstant.BASE64_SECRET)
                    .compact());
            return auth;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("机构信息加密失败，请稍候再试");
        }

    }

    @GetMapping("/customer_config")
    public ApiResponse getCustomerConfig() {
        return new ApiResponse<>(customerConfig.getData());
    }
}
