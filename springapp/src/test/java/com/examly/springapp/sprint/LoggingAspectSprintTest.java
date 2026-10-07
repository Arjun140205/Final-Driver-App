package com.examly.springapp.sprint;

import com.examly.springapp.aspect.LoggingAspect;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggingAspectSprintTest {
    @Test
    void declaresCentralBeforeAndAfterAdvice() throws Exception {
        assertTrue(LoggingAspect.class.getMethod("logMethodStart", org.aspectj.lang.JoinPoint.class)
                .isAnnotationPresent(Before.class));
        assertTrue(LoggingAspect.class.getMethod("logMethodCompletion", org.aspectj.lang.JoinPoint.class)
                .isAnnotationPresent(After.class));
    }
}
