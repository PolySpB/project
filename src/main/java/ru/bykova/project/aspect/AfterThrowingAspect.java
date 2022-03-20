package ru.bykova.project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(3)
@Component
public class AfterThrowingAspect {

    @Pointcut("execution(* * (..))")
    public void allMethod() {

    }

    @AfterThrowing(value = "allMethod()", throwing = "exception")
    public void afterAllExceptionAdvice(JoinPoint joinPoint, Exception exception) {
        log.error("afterAllExceptionAdvice:: {} finished with exception: {}", joinPoint.toShortString(), exception.getMessage());
    }
}
