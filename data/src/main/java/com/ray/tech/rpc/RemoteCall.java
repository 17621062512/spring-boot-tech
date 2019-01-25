package com.ray.tech.rpc;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RemoteCall {
    @Resource
    private RestTemplate restTemplate;

    public List getDataSourceByCategoryAndCascaderCode(String category, String areaCode) {
        String url = "https://www.baidu.comJsonResponse";
        Map<String, String> params = new HashMap<>();
        Object response = restTemplate.getForObject(url, Object.class, params);
        log.info(JSON.toJSONString(response));
        return new ArrayList();
    }
}
