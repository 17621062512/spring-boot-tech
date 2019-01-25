package com.ray.tech.service;

import com.ray.tech.model.SubscribeUser;
import com.ray.tech.repository.SubscribeUserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class SubscribeUserService {

    @Resource
    private SubscribeUserRepository subscribeUserRepository;


    public void subscribe(SubscribeUser subscribeUser) {
        subscribeUser.setUpdateTime(System.currentTimeMillis());
        subscribeUserRepository.save(subscribeUser);
    }

    public void unsubscribe(SubscribeUser subscribeUser) {
        if (null != subscribeUser) {
            subscribeUser.setUpdateTime(System.currentTimeMillis());
            subscribeUser.setSubscribe(false);
            subscribeUserRepository.save(subscribeUser);
        }
    }


    public SubscribeUser findByOpenId(String openId) {
        return subscribeUserRepository.findById(openId).orElse(null);
    }


    public void recordOperations(String openid, String merchantId) {
        Optional<SubscribeUser> op = subscribeUserRepository.findById(openid);
        if (op.isPresent()) {
            SubscribeUser one = op.get();
            one.setLastLocation(merchantId);
            one.setLastOperationTime(System.currentTimeMillis());
            subscribeUserRepository.save(one);
        }
    }


}
