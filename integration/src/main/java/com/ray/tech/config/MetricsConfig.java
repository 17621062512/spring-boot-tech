package com.ray.tech.config;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.ray.tech.utils.IpUtils;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {
    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        String localIp = "127.0.0.1";
        try {
            localIp = IpUtils.getLocalIP();
        } catch (Exception e) {
            log.error("MetricsConfig获取本机ip失败", e);
        }


        //输出度量数据至控制台
        registerReporter(ConsoleReporter.forRegistry(metricRegistry)
                .formattedFor(Locale.CHINA)
                .build())
                .start(20, TimeUnit.SECONDS);
        //输出度数数据到influxdb
        String dbServer = "127.0.0.1";

//        registerReporter(InfluxdbReporter.forRegistry(metricRegistry)
//                //influxdb配置
//                .protocol(new HttpInfluxdbProtocol(dbServer, 8086))
//                //influxdb版本小于等于08.时需要配置
////                .v08()
//                //速率单位
//                .convertRatesTo(TimeUnit.SECONDS)
//                //持续时间单位
//                .convertDurationsTo(TimeUnit.MILLISECONDS)
//                .transformer(new KeyValueMetricMeasurementTransformer())
//                //报告过滤器
//                .filter(MetricFilter.ALL)
//                //仅在metrics变化时报告
//                .skipIdleMetrics(false)
//                //度量标签
//                .tag("ip", localIp)
//                .build())
//                //轮训间隔
//                .start(5, TimeUnit.SECONDS);
//
//
//        registerReporter(GraphiteReporter.forRegistry(metricRegistry)
//                .convertRatesTo(TimeUnit.SECONDS)
//                .convertDurationsTo(TimeUnit.MILLISECONDS)
//                .filter(MetricFilter.ALL)
//                .build(new Graphite("127.0.0.1", 8080)))
//                .start(5, TimeUnit.SECONDS);

    }
}
