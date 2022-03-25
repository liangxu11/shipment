package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-17 14:08:49
 * @return * @return: null
 **/
@Slf4j
@Aspect
@Order(3)
@Component
public class ParamValidAspect {

    @Around("execution(public * com.example.demo.controller..*.*(..)) && args(..,errors)")
    public Object validateParam(ProceedingJoinPoint pjp, Errors errors) throws Throwable {

        if (errors.hasErrors()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            request.setAttribute("body", null);

            throw new IllegalArgumentException(errors.getFieldError().getDefaultMessage());
        }

        return pjp.proceed();
    }
}
