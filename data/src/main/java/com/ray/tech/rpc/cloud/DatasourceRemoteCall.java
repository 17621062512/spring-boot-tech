package com.ray.tech.rpc.cloud;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DatasourceRemoteCall {

    @Resource
    private RestTemplate restTemplate;

    public List getDataThroughLoadBalance(String param) {
        String url = "http://spring-tech-integration/";
        Map<String, String> params = new HashMap<>();
        Object response = restTemplate.getForObject(url, Object.class, params);
        log.info(JSON.toJSONString(response));
        return new ArrayList();
    }
}


