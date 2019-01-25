package com.ray.tech.service;

import com.ray.tech.model.Merchant;
import com.ray.tech.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MerchantService {

    @Resource
    private MerchantRepository merchantRepository;

    public Merchant findBy(String merchantId) {
        return merchantRepository.findById(merchantId).orElse(new Merchant());
    }


}
