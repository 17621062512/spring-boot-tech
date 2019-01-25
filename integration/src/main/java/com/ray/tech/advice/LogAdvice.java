package com.ray.tech.advice;

import com.alibaba.fastjson.JSON;

import com.beust.jcommander.internal.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;


@Slf4j
@Aspect
@Component
public class LogAdvice {
    @Pointcut("@annotation(com.ray.tech.advice.LogAspect)")
    public void pointCutMethod() {

    }

    @Before("pointCutMethod()")
    public void asbefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String declaringTypeName = signature.getDeclaringTypeName();
        String name = signature.getName();
        log.info("Name:" + declaringTypeName + ":" + name);
        log.info("Args:" + Lists.newArrayList(args).toString());
    }

    @After("pointCutMethod()")
    public void asafter(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        log.info(name + " end");
    }

    @AfterReturning(returning = "reObject", pointcut = "pointCutMethod()")
    public void asafterReturn(Object reObject) {
        Class<?> clazz = reObject.getClass();
        String reClass = clazz.getName();
        Field[] declaredFields = clazz.getDeclaredFields();
        ArrayList<Field> fields = (ArrayList<Field>) Lists.newArrayList(declaredFields);
        log.info("Return Class:" + reClass);
        log.info("Fields Json" + JSON.toJSONString(fields));
    }
}
