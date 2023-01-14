package com.project.shopping.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("within(com.project.shopping.controller..*)")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("start - {} / {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        logger.info("finished {} ms - {} / {}",totalTimeMillis, joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        return result;
    }
}
