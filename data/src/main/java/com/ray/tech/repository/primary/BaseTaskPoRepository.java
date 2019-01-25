package com.ray.tech.repository.primary;

import com.ray.tech.model.BaseTaskPo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseTaskPoRepository extends MongoRepository<BaseTaskPo, String> {
    @Query(value = "{'cellPhoneNum':?0}", fields = "{'name':1,'token':1}")
    List<BaseTaskPo> customQuery(String cellPhoneNum);
}
