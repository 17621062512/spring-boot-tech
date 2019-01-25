package com.ray.tech.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface QuartzScheduled {
    /**
     * @see org.quartz.CronExpression
     */
    @AliasFor("cron")
    String value() default "";

    @AliasFor("value")
    String cron() default "";

    String description() default "";

    String group() default "default_trigger_group";

    String name() default "";

    /**
     * e.g
     *
     * @see org.quartz.Trigger#MISFIRE_INSTRUCTION_SMART_POLICY
     * @see org.quartz.CronTrigger#MISFIRE_INSTRUCTION_FIRE_ONCE_NOW  defalut
     * @see org.quartz.SimpleTrigger#MISFIRE_INSTRUCTION_FIRE_NOW  defalut
     */
    int misfireInstruction() default 1;
}
