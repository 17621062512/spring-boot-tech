package com.ray.tech.service;

import com.ray.tech.model.BaseTaskPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class BizTaskService {

    @Resource
    private MongoTemplate mongoTemplate;

    public  Page<BaseTaskPo> findByOrganizationAccount(BaseTaskPo queryValues, Long start, Long end, int pageIndex, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Order.desc("createTime")));
        UntypedExampleMatcher matcher = UntypedExampleMatcher.
                matchingAll().
                withIgnoreNullValues();
        Example<BaseTaskPo> example = Example.of(queryValues, matcher);
        Query query = Query.query(Criteria.byExample(example).and("createTime").gte(start).lte(end)).with(pageRequest);
        long count = mongoTemplate.count(query, BaseTaskPo.class);
        List<BaseTaskPo> list = mongoTemplate.find(query.with(pageRequest), BaseTaskPo.class);
        list.forEach(task -> {
            task.setClientIp(null);
            task.setOrganizationAccount(null);
            task.setName(null);
            task.setIdCardNum(null);
            task.setCellPhoneNum(null);
        });
        Page<BaseTaskPo> pageData = new PageImpl<>(list, pageRequest, count);

        return pageData;
    }

}
