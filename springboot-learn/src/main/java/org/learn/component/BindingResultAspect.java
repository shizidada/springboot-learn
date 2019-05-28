package org.learn.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.learn.common.api.AjaxResult;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


/**
 * HibernateValidator错误结果处理切面
 */
@Aspect
@Component
@Order(2)
public class BindingResultAspect {
    @Pointcut("execution(public * org.learn.controller.*.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError fieldError = result.getFieldError();
                    if (fieldError != null) {
                        return AjaxResult.failure(fieldError.getDefaultMessage());
                    } else {
                        return AjaxResult.failure();
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}
