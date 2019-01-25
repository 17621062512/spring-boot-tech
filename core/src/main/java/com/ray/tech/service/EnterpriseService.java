package com.ray.tech.service;

import com.ray.tech.model.OrganizationBo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author JacobDong
 * @date 2018/5/6 14:07
 */
@Slf4j
public class EnterpriseService {
    public static OrganizationBo getOrganizationByAccountAndPassword(String account, String password) throws IOException {
        OrganizationBo organizationBo = new OrganizationBo();
        organizationBo.setAccount(account);
        return organizationBo;
    }
}
