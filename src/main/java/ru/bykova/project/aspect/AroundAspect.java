package ru.bykova.project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.bykova.project.model.ExternalInfo;

import java.util.Objects;

@Slf4j
@Aspect
@Component
public class AroundAspect {

    @Value("${id-not-process}")
    private Integer id;

    @Pointcut("execution(* *(.., ru.bykova.project.model.ExternalInfo, ..)) && args(externalInfo) && @annotation(ru.bykova.project.annotation.CheckRequest)")
    public void allMethodWithCheckRequestAnnotationAndExternalExternalInfo(ExternalInfo externalInfo) {

    }

    @Around("allMethodWithCheckRequestAnnotationAndExternalExternalInfo(externalInfo)")
    public void aroundAllMethodWithCheckRequestAnnotationAndExternalExternalInfo(ProceedingJoinPoint pjp, ExternalInfo externalInfo) {
        log.info("aroundAllMethodWithCheckRequestAnnotationAndExternalExternalInfo:: {} with arguments: {}", pjp.getSignature().toShortString(), externalInfo);

        if (Objects.equals(externalInfo.getId(), id)) {
            return;
        }
    }
}
