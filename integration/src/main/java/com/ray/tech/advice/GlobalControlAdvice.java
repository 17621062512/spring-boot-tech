package com.ray.tech.advice;

import com.ray.tech.exception.GlobalExceptionHandler;
import com.ray.tech.model.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NestedRuntimeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;

/**
 * @author 全局的controller异常捕捉
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class})
//@ControllerAdvice(value = {"com.ray.tech.controller"})
public class GlobalControlAdvice {


    @Value("${spring.application.name}")
    private String projectName;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResponse exceptionHandle(Exception e) throws Exception {
        if (e instanceof ServletException || e instanceof NestedRuntimeException) {
            throw e;
        }
        return GlobalExceptionHandler.convertExceptionToJsonResponse(e);
    }

}
