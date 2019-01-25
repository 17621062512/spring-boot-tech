package com.ray.tech.repository;

import com.ray.tech.model.SubscribeUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeUserRepository extends PagingAndSortingRepository<SubscribeUser, String> {
    List<SubscribeUser> findByLastLocationAndLastOperationTimeBetween(String lastLocation, long from, long to);
}
