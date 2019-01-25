package com.ray.tech.task;

import com.alibaba.fastjson.JSON;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * quartz任务调度集群
 * <a>https://blog.csdn.net/zl_momomo/article/details/83584315</a>
 * <a>https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-quartz</a>
 */
@Slf4j
@Service
public class TimeSeriesTask {
    private static AtomicLong rate = new AtomicLong(0L);
    @Resource
    private MetricRegistry metricRegistry;
    @Scheduled(fixedRate = 1000L)
    public void doTask() {
        String name = "tag_key.tag_value.series_name2";
        metricRegistry.counter(name).inc();
    }


}
