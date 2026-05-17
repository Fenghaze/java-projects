package com.fenghaze.mybatis.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect // AOP切面类
public class TimeAspect {
  @Around("execution(* com.fenghaze.mybatis.service..*(..))")
  public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object result = pjp.proceed();
    long endTime = System.currentTimeMillis();
    return result;
  }
}
