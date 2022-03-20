package ru.bykova.project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class CacheResultAspect {

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    @Pointcut("@annotation(ru.bykova.project.annotation.CacheResult)")
    public void allMethodWithCacheResultAnnotation() {

    }

    @Around(value = "allMethodWithCacheResultAnnotation()")
    public Object aroundAllMethodWithCacheResultAnnotation(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String shortSignature = signature.toShortString();
        Object[] args = pjp.getArgs();

        log.info("aroundAllMethodWithCacheResultAnnotation:: {} with arguments: {}", shortSignature, args);

        String key = String.format("%s%s", shortSignature, Arrays.toString(args));
        Object result = CACHE.get(key);

        if (result != null) {
            log.info("{} has cache:: key={}, value={}", shortSignature, key, result);
        } else {
            log.info("{} doesn't have cache", shortSignature);
            result = pjp.proceed();
            CACHE.put(key, result);
            log.info("method result for {} have been cached:: key={}, value={}", shortSignature, key, result);
        }

        return result;
    }
}
