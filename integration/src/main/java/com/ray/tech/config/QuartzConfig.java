package com.ray.tech.config;

import com.ray.tech.annotation.QuartzScheduled;
import com.ray.tech.task.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;
import java.util.Map;

/**
 * @author EndEcho
 * jdbc store需要的sql脚本
 * <a>https://github.com/quartz-scheduler/quartz/tree/9f9e400733f51f7cb658e3319fc2c140ab8af938/quartz-core/src/main/resources/org/quartz/impl/jdbcjobstore</a>
 */
@Slf4j
@Configuration
@AutoConfigureAfter({QuartzAutoConfiguration.class})
public class QuartzConfig {
    public QuartzConfig(SchedulerFactoryBean factory, ApplicationContext context) {
        init(factory, context);
    }

    public void init(SchedulerFactoryBean factory, ApplicationContext context) {
        Map<String, Object> jobsBeans = context.getBeansWithAnnotation(QuartzScheduled.class);
        Scheduler scheduler = factory.getScheduler();
        factory.setOverwriteExistingJobs(true);
        jobsBeans.forEach((beanName, instance) -> {
            try {
                Class<?> instanceClass = instance.getClass();
                QuartzScheduled annotation = instanceClass.getAnnotation(QuartzScheduled.class);
                JobDetail jobDetail = createJobDetail(instanceClass, annotation);
                CronTrigger cronTrigger = createCronTrigger(jobDetail, annotation);
                scheduler.scheduleJob(jobDetail, cronTrigger);
            } catch (ParseException | SchedulerException e) {
                log.error("加载定时任务失败,BeanName:{}", beanName, e);
            }
        });
    }


    private static CronTrigger createCronTrigger(JobDetail jobDetail, QuartzScheduled annotation) throws ParseException {
// TODO       CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 40 9 * * ? ")).build();
        String name = annotation.name();
        if (StringUtils.isBlank(name)) {
            name = jobDetail.getJobClass().getName();
        }
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setGroup(annotation.group());
        String cron = annotation.cron();
        StringUtils.isBlank(cron);
        cron = annotation.value();
        factoryBean.setCronExpression(cron);
        factoryBean.setMisfireInstruction(annotation.misfireInstruction());
        factoryBean.setName(name);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    private static JobDetail createJobDetail(Class jobClass, QuartzScheduled annotation) {

//
        String name = annotation.name();
        if (StringUtils.isBlank(name)) {
            name = jobClass.getName();
        }
        return JobBuilder.newJob(QuartzJob.class)
                .withIdentity(name, annotation.group())
                .withDescription(annotation.description())
                .storeDurably()
                .build();
        //<editor-fold desc="旧版创建方式">
        //        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
//        factoryBean.setJobClass(jobClass);
//        factoryBean.setDurability(true);
//        factoryBean.setName(name);
//        factoryBean.setGroup(annotation.group());
//        factoryBean.setDescription(annotation.description());
//        factoryBean.afterPropertiesSet();
//        return factoryBean.getObject();
        //</editor-fold>
    }
}
