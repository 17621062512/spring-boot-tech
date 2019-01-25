package com.ray.tech.repository;

import com.ray.tech.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends PagingAndSortingRepository<Merchant, String> {

    /**
     * 根据账号密码查找商铺
     *
     * @param account  账号
     * @param password 密码
     * @return Merchant
     */
    Merchant findByAccountAndPassword(String account, String password);

    Page<Merchant> findByNameContainsAndIdNotContains(Pageable pageable, String name, String manager);

    Merchant findByAccount(String account);


}
