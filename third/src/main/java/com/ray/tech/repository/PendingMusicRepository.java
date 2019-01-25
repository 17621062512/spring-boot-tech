package com.ray.tech.repository;


import com.ray.tech.model.PendingMusic;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingMusicRepository extends PagingAndSortingRepository<PendingMusic, String> {




}
