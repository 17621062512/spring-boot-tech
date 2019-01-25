package com.ray.tech;

import com.ray.tech.config.AsyncConfig;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * <a>https://docs.spring.io/spring-boot/docs/2.2.0.BUILD-SNAPSHOT/reference/html/appendix.html#appendix</a>
 */
@EnableCaching
//@EnableEurekaClient
@SpringBootApplication
@EnableMetrics
@EnableScheduling
public class IntegrationApplication {

    //    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }

    public static void fluentAppBuilder(String[] args) {
        new SpringApplicationBuilder()
                .sources(AsyncConfig.class)//configuration classes and components
//                .child()//Create a child application
                .run(args);
    }

}
