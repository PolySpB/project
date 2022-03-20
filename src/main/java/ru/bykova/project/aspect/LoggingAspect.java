package ru.bykova.project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@Order(2)
public class LoggingAspect {

    @Before("within(ru.bykova.project.service.*)")
    public void beforeAllServiceAdvice(JoinPoint joinPoint) {
        log.info("beforeAllServiceAdvice:: call {} with arguments: {} from .service package", joinPoint.toShortString(), joinPoint.getArgs());
    }

    @After("within(ru.bykova.project.service.*)")
    public void afterAllServiceAdvice(JoinPoint joinPoint) {
        log.info("beforeAllServiceAdvice:: finish {} with arguments: {} from .service package", joinPoint.toShortString(), joinPoint.getArgs());
    }

}
