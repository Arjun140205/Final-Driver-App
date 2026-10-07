package com.examly.springapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(public * com.examly.springapp.controller..*(..)) || execution(public * com.examly.springapp.service..*(..))")
    public void logMethodStart(JoinPoint joinPoint) {
        log.debug("Starting {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @After("execution(public * com.examly.springapp.controller..*(..)) || execution(public * com.examly.springapp.service..*(..))")
    public void logMethodCompletion(JoinPoint joinPoint) {
        log.debug("Completed {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }
}
