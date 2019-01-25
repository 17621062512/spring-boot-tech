package com.ray.tech.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.ray.tech.constant.WechatPayApiURL;
import com.ray.tech.constant.WechatPayConstants;
import com.ray.tech.model.Merchant;
import com.ray.tech.model.PendingMusic;
import com.ray.tech.repository.MerchantRepository;
import com.ray.tech.repository.PendingMusicRepository;
import com.ray.tech.utils.OkHttpUtils;
import com.ray.tech.utils.WechatMessageUtil;
import com.ray.tech.constant.AlipayConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
public class PayService {
    @Resource
    private MerchantRepository merchantRepository;
    @Resource
    private PendingMusicRepository pendingMusicRepository;
    /**
     * This class is immutable and thread-safe.
     * 这个类是不可变且线程安全的
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    /**
     * @param clientIp  下单客户的IP地址
     * @param merchant
     * @param tradeType
     * @return 微信的订单详情
     * @see <a href="https://github.com/Wechat-Group/weixin-java-tools">微信Java开发工具包</a>
     */
    public Map<String, String> wechatPay(String clientIp, PendingMusic bill, Merchant merchant, String tradeType) {

        //生成订单开始和结束时间，信用卡支付不得小于1分钟，其他5分钟，(目前关闭了信用卡支付方式，见WXPayConstants配置)
        LocalDateTime now = LocalDateTime.now();
        String timeStart = now.format(DATE_TIME_FORMATTER);
        LocalDateTime expire = now.plus(WechatPayConstants.EXPIRY_TIME, ChronoUnit.MINUTES);
        String timeExpire = expire.format(DATE_TIME_FORMATTER);
        //获取订单请求数据，所有需要发送的参数都在这里
        HashMap<String, String> preData = WechatPayApiURL.getUnifiedOrderRequestArgs(bill.getId(), clientIp, String.valueOf(bill.getFee()), bill.getUserOpenId(), timeStart, timeExpire, merchant.getName(), tradeType);
        log.info(preData.toString());

        //默认状态为失败
//        bill.setTimeStart(timeStart);
//        bill.setTimeExpire(timeExpire);
        bill.setStatus(PendingMusic.BillStatus.FAILED);
        Map<String, String> respMap;
        try {
            //调用微信统一下单接口(接口名字是微信起的，别吐槽我)
            respMap = OkHttpUtils.postXMLForObject(WechatPayApiURL.UNIFIED_ORDER, preData);
        } catch (Exception e) {
            log.error(e.getMessage());
            return billFailed(bill);
        }
        if (respMap == null) {
            return billFailed(bill);
        }
        String returnCode = respMap.get("return_code");
        if (returnCode.equals(WechatPayConstants.FAIL)) {
            log.info("请求失败 returnCode:" + returnCode);
            return billFailed(bill);
        } else if (returnCode.equals(WechatPayConstants.SUCCESS)) {
            String resultCode = respMap.get("result_code");
            if (resultCode.equals(WechatPayConstants.SUCCESS)) {
                log.info("业务成功 resultCode:" + resultCode);
                //预支付交易会话标识     (微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时)
                //放到本公司订单中
//                String prepayId = respMap.get("prepay_id");
//                bill.setPrepayId(prepayId);
                bill.setStatus(PendingMusic.BillStatus.NOT_PAY);
                pushNewBill(bill);
            } else {
                return billFailed(bill);
            }

        }
        log.info("当前订单状态" + bill.toString());
        return respMap;
    }

    @Transactional
    public String validateWechatCallBack(Map<String, String> wxCallBack) {
        //1.签名验证 ,2.订单号验证,3.重复通知验证（本地添加是否已经通知微信字段）
        //4.验证本地订单状态, 5.验证业务结果, 6.订单金额验证
        String returnMsg = WechatPayConstants.FAIL;
        String openid = wxCallBack.get("openid");
        PendingMusic bill = null;
        try {
            //1.签名验证
            if (WechatMessageUtil.isSignatureValid(wxCallBack)) {
                String returnCode = wxCallBack.get("return_code");
                if (returnCode.equals(WechatPayConstants.FAIL)) {
                    log.error("微信回调接口 return_msg:" + wxCallBack.get("return_msg"));
                } else if (returnCode.equals(WechatPayConstants.SUCCESS)) {
                    //2.订单号验证
                    String billId = wxCallBack.get("out_trade_no");
                    bill = query(billId);
                    if (bill != null) {
                        //3.重复通知验证
                        if (bill.getIsSent()) {
                            //如果是重复通知，直接返回SUCCESS
                            //return 'SUCCESS'
                            returnMsg = WechatPayConstants.SUCCESS;
                            log.info(billId + " 该订单以向微信支付返回过验证结果，直接返回SUCCESS");
                        } else {
                            //4.验证本地订单状态
                            if (bill.getStatus().equals(PendingMusic.BillStatus.NOT_PAY)) {
                                //5.验证业务结果
                                if (wxCallBack.get("result_code").equals(WechatPayConstants.SUCCESS)) {
                                    //6.订单金额验证
                                    returnMsg = validateFee(wxCallBack, returnMsg, bill);
                                } else {
                                    bill.setStatus(PendingMusic.BillStatus.PAY_ERROR);
                                    log.error("支付失败");
                                }
                            }
                            bill.setIsSent(true);
                        }
                        PendingMusic pendingMusic = pushBill(bill);
                        log.info("更新订单状态为:" + pendingMusic.toString());
                    } else {
                        log.error("帐单号不一致 bill ID :" + billId);
                    }
                }

            } else {
                log.error("签名不合法");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("签名验证失败");
        }
        if (returnMsg.equals(WechatPayConstants.SUCCESS)) {
            try {
                Merchant one = merchantRepository.findById(bill.getMerchantId()).orElse(null);
                one.add2TotalIncome(Math.toIntExact(bill.getFee()));
                merchantRepository.save(one);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String merchantId = "failed";
            if (bill != null) {
                merchantId = bill.getMerchantId();
            }
        }
        return returnMsg;
    }

    public JSONObject alipay(String id) {
        Merchant one = merchantRepository.findById(id).orElse(null);
        if (one == null) {
            return null;
        }
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstant.SERVER_URL,
                AlipayConstant.APPID,
                AlipayConstant.ALIPAY_PRIVATE_KEY,
                AlipayConstant.FORMAT,
                AlipayConstant.CHARSET,
                AlipayConstant.ALIPAY_PUBLIC_KEY,
                AlipayConstant.SIGN_TYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.wap.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(transAlipayAppModel(one));
        request.setNotifyUrl(AlipayConstant.NOTIFY_URL);
        AlipayTradeAppPayResponse response;
        JSONObject jsonObject;
        try {
            response = alipayClient.sdkExecute(request);
            String sign = AlipaySignature.rsa256Sign(JSON.toJSONString(response.getBody()), AlipayConstant.ALIPAY_PRIVATE_KEY, "UTF-8");
            jsonObject = JSON.parseObject(JSON.toJSONString(response));
            jsonObject.put("sign", sign);
            return jsonObject;
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            return null;
        } catch (NullPointerException e) {
            //支付宝无响应信息
            e.printStackTrace();
            return null;
        }
        //TODO 调用成功，则处理业务逻辑
    }

    public void aliCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("------------------------alipay_callback-----------------------");
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Object key : requestParams.keySet()) {
            String name = (String) key;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        log.info(JSON.toJSONString(params));
        String status = params.get("trade_status");
        String billId = params.get("passback_params");
        PayStatusCache payStatusCache = new PayStatusCache();
        Merchant contractBill = merchantRepository.findById(billId).orElse(null);
//        String userId = contractBill.getBorrower().getAccount();
        if ("TRADE_SUCCESS".equals(status) || "TRADE_FINISHED".equals(status)) {
            if (String.valueOf(contractBill.getFee() / 100.0).equals(params.get("receipt_amount"))) {
//                try {
////                    contractService.afterPaySuccess(contractBill);
//                    payStatusCache.setPayResult(PayStatusCache.PayResult.CA_SUCCESS);
//                    contractBill.setExamineStatus(ContractBill.ExamineStatus.UNCHECK);
//                } catch (Exception e) {
//                    payStatusCache.setPayResult(PayStatusCache.PayResult.CA_FAIL);//CA失败，回款到余额
//                    User user = contractBill.getBorrower();
//                    log.info("回款前余额" + user.getBalance());
//                    user.setBalance(user.getBalance() + contractBill.getFee());
//                    log.info("回款后余额" + user.getBalance());
//                    userRepository.save(user);
//                    contractBill.setExamineStatus(ContractBill.ExamineStatus.CA_FAILED);
//                }
//                contractBillRepository.save(contractBill);
//                payStatusCache.setUserId(userId);
//                payStatusCache.setBillId(billId);
//                payStatusCache.setPayType(PayStatusCache.PayType.ALIPAY);
//                PayStatusCacheUtils.setPayStatus(payStatusCache);

            }
        } else {
//            payStatusCache.setUserId(userId);
//            payStatusCache.setPayResult(PayStatusCache.PayResult.PAY_FAIL);
//            payStatusCache.setBillId(billId);
//            payStatusCache.setPayType(PayStatusCache.PayType.ALIPAY);
//            PayStatusCacheUtils.setPayStatus(payStatusCache);
//            contractBill.setExamineStatus(ContractBill.ExamineStatus.PAY_FAIL);
//            contractBillRepository.save(contractBill);
        }

//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)

//        try {
//            boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
//            log.info("阿里签名校验结果：" + flag);
//            if (flag) {
        response.getWriter().write("success");
//            } else {
//                response.getWriter().write("failure");
//            }
//        } catch (AlipayApiException e) {
//            response.getWriter().write("failure");
//        }

    }

    private PendingMusic query(String id) {
        return pendingMusicRepository.findById(id).orElse(null);
    }

    private String validateFee(Map<String, String> wxCallBack, String returnMsg, PendingMusic bill) {
        long localFee = bill.getFee();
        String localOpenId = bill.getUserOpenId();
        String tradeType = wxCallBack.get("trade_type");
        String wxOpenId = wxCallBack.get("openid");
        String wxFee = wxCallBack.get("total_fee");
        //return 'SUCCESS' | finally,订单支付成功,更新本地订单状态 当交易方式（trade_type）是NATIVE时，不判断openid
        if ((tradeType.equals(WechatPayConstants.TRADE_TYPE_NATIVE) || localOpenId.equals(wxOpenId)) && localFee == Long.valueOf(wxFee)) {
            String timeEnd = wxCallBack.get("time_end");
            returnMsg = WechatPayConstants.SUCCESS;
            bill.setTimeEnd(timeEnd);
            bill.setStatus(PendingMusic.BillStatus.PAY_SUCCESS);
        } else {
            bill.setStatus(PendingMusic.BillStatus.PAY_ERROR);
            log.error(bill.getId() + " 订单的金额" + localFee + "￥ 与 微信回调的金额" + wxFee + "￥ 不一致");
        }

        return returnMsg;
    }

    private Map<String, String> billFailed(PendingMusic bill) {
        bill.setStatus(PendingMusic.BillStatus.FAILED);
        pushNewBill(bill);
        return null;
    }

    private static AlipayTradeAppPayModel transAlipayAppModel(Merchant bill) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("聚凭证");
        model.setSubject("聚凭证");
        //todo
        model.setOutTradeNo(UUID.randomUUID().toString());
        model.setTotalAmount(String.valueOf(bill.getFee() / 100.0));
        model.setPassbackParams(bill.getId());
        return model;
    }

    private PendingMusic pushNewBill(PendingMusic bill) {
        bill.setCreateTime(System.currentTimeMillis());
        bill.setConsumed(false);
        return pushBill(bill);
    }

    @Data
    public static class PayStatusCache {
        private PayType payType;
        private String billId;
        private String userId;
        private PayResult payResult;

        public enum PayType {
            ALIPAY,
            WECHAT,
            BALANCE;
        }

        public enum PayResult {
            UNKNOW,
            CA_SUCCESS,
            PAY_FAIL,
            CA_FAIL,
        }

    }

    private PendingMusic pushBill(PendingMusic bill) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        bill.setNewestUpdTime(dtf.format(LocalDateTime.now()));
        return pendingMusicRepository.save(bill);
    }


}
