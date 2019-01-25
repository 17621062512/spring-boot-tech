package com.ray.tech.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtils {
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final int HTTP_STATUS_OK = 200;

    private static int CONNECT_TIMEOUT = 10;
    private static int READ_TIMEOUT = 15;
    private static final int MAX_IDLE_CONNECTIONS = 15;
    private static final int KEEP_ALIVE_DURATION = 15;

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient
            .Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
            .build();


    /**
     * 获取 响应的字符串内容
     *
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws IOException
     */
    public static String getForResponseString(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        String responseString = null;
        Response okHttpResponse = getOkHttpResponse(url, params, headers);
//        if (okHttpResponse != null && okHttpResponse.networkResponse() != null) {
//            log.info("URL:{}   ，从发送到收到返回耗时：{} ms", url, okHttpResponse.networkResponse().receivedResponseAtMillis() - okHttpResponse.networkResponse().sentRequestAtMillis());
//        }
        checkResponseStatus(okHttpResponse, url);
        if (okHttpResponse.isSuccessful()) {
            ResponseBody responseBody = okHttpResponse.body();
            if (null != responseBody) {
                responseString = responseBody.string();
            }
        }
        return responseString;
    }


    /**
     * 发送 getForResponseBody 请求
     *
     * @param url     请求地址
     * @param params  请求参数对的map
     * @param headers 请求header的map
     * @return OkHttp ResponseBody
     * @throws IOException
     */
    public static ResponseBody getForResponseBody(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        Response okHttpResponse = getOkHttpResponse(url, params, headers);
        checkResponseStatus(okHttpResponse, url);
        return okHttpResponse.body();
    }

    /**
     * OkHttp 提交post请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static ResponseBody postFormForResponseBody(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder builder = new FormBody.Builder();
        formBuilderWithFormParams(builder, params);
        requestBuilderWithHeaders(requestBuilder, headers);
        RequestBody requestBodyPost = builder.build();

        Request request = requestBuilder
                .url(url)
                .post(requestBodyPost)
                .build();

        Response response = OK_HTTP_CLIENT
                .newCall(request)
                .execute();

        checkResponseStatus(response, url);
        return response.body();
    }


    /**
     * postFormForResponseBody send json body
     *
     * @param url
     * @param json json 字符串
     * @return
     * @throws IOException
     */
    public static ResponseBody postJsonForResponseBody(String url, String json, Map<String, String> headers) throws IOException {
        return postJsonForResponse(url, json, headers).body();
    }

    public static Response postJsonForResponse(String url, String json, Map<String, String> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilderWithHeaders(requestBuilder, headers);
        Request request = requestBuilder.addHeader("Connection", "false")
                .url(url)
                .post(RequestBody.create(JSON_TYPE, json))
                .build();
        Response response = OK_HTTP_CLIENT
                .newCall(request)
                .execute();
        checkResponseStatus(response, url);
        return response;
    }

    /**
     * 检查http状态码是否非200 状态
     *
     * @param response
     * @param url
     */
    private static void checkResponseStatus(Response response, String url) {
        if (response.code() != HTTP_STATUS_OK) {
            try {
                log.error("okHTTP 请求失败 {} {}", response.code(), url);
            } finally {
                response.close();
            }
        }
    }

    public static int curlHttpStatus(String url) throws Exception {
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(url)
                .get()
                .build();
        Response response = OK_HTTP_CLIENT
                .newCall(request)
                .execute();
        response.close();
        return response.code();
    }


    /**
     * GET 请求方法获取 OkHttp 包装的响应
     *
     * @param url     请求地址
     * @param params  请求参数对的map
     * @param headers 请求header的map
     * @return OkHttp 包装的响应
     * @throws IOException
     */
    private static Response getOkHttpResponse(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        Request.Builder builder = new Request.Builder();
        url = getUrlWithUrlParams(url, params);

//        log.debug(">>> OkHttp 请求地址为:", url);
        requestBuilderWithHeaders(builder, headers);

        Request request = builder
                .url(url)
                .get()
                .build();

        return OK_HTTP_CLIENT.newCall(request).execute();
    }
    public static Map<String, String> postXMLForObject(String url, Map<String, String> data) throws Exception {
        //生成参数签名
        String xml = WechatMessageUtil.generateSignedXml(data);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml"), xml);
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .build();
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        ResponseBody body = response.body();
        if (body != null) {
            String string = body.string();
            //包含检测是否成功，签名正确性
            return WechatMessageUtil.processResponseXml(string);
        } else {
            return null;
        }
    }
    /**
     * 通过初始URL 以及URL 参数获取完整的请求地址
     *
     * @param url    基础URL
     * @param params URL 内的参数key value 键值对
     * @return 完整的url
     */
    private static String getUrlWithUrlParams(String url, Map<String, String> params) {
        if (!url.contains("?")) {
            url += "?timestamp=" + System.currentTimeMillis();
        }
        StringBuilder appendUrl = new StringBuilder();
        if (null != params) {
            params.forEach((key, value) -> {
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    appendUrl.append("&").append(key).append("=").append(value);
                }
            });
            url = url + appendUrl.toString();
            log.debug(String.format("调用地址为:%s", url));
            log.debug(String.format("输入参数为:%s", JSONObject.toJSON(params)));
        }
        return url;
    }

    private static void formBuilderWithFormParams(FormBody.Builder builder, Map<String, String> params) {
        if (null != params) {
            params.forEach((key, value) -> {
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    builder.add(key, value);
                }
            });
        }
    }

    private static void requestBuilderWithHeaders(Request.Builder builder, Map<String, String> headers) {
        if (null != headers) {
            headers.forEach((key, value) -> {
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    builder.addHeader(key, value);
                }
            });
        }
    }
}

