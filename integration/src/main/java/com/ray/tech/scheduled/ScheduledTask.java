package com.ray.tech.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JacobDong
 * @date 2018/5/4 18:23
 */
@Slf4j
@Component
@EnableScheduling
public class ScheduledTask {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate = 1000  * 10)
    public void secondTask() {
        log.info(sdf.format(new Date(System.currentTimeMillis())));
    }
}
