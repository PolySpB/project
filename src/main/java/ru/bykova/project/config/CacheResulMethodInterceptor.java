package ru.bykova.project.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import ru.bykova.project.annotation.CacheResult;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CacheResulMethodInterceptor implements MethodInterceptor {

    private static final Map<String, Map<MethodArgs, Object>> CACHE = new ConcurrentHashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        boolean isAnnotationPresent = false;
        Method invocationMethod = invocation.getMethod();

        Method[] declaredMethods = invocation.getThis().getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (invocationMethod.getName().equals(method.getName()) &&
                    AnnotationUtils.findAnnotation(method, CacheResult.class) != null) {
                log.info("{} is annotated with CacheResult", invocationMethod.getName());
                isAnnotationPresent = true;
                break;
            }
        }

        if (isAnnotationPresent) {
            String fullMethodName = getFullMethodName(invocationMethod);
            Map<MethodArgs, Object> methodArgsObjectMap = CACHE.get(fullMethodName);

            if (methodArgsObjectMap != null) {
                log.info("{} has cache: {}", fullMethodName, methodArgsObjectMap);
                MethodArgs invocationArgs = getMethodArgs(invocation.getArguments());
                Object result = methodArgsObjectMap.get(invocationArgs);

                if (result != null) {
                    log.info("getting from cache {}({}) result: {}", fullMethodName, invocation.getArguments(), result);
                    return result;
                } else {
                    log.info("method result for {} is missing", fullMethodName);
                    result = invocation.proceed();
                    methodArgsObjectMap.put(invocationArgs, result);
                    log.info("method result for {} have been cached", fullMethodName);
                    return result;
                }

            } else {
                log.info("{} doesn't have cache", fullMethodName);
                Object result = invocation.proceed();
                methodArgsObjectMap = new ConcurrentHashMap<>();
                methodArgsObjectMap.put(getMethodArgs(invocation.getArguments()), result);
                CACHE.put(fullMethodName, methodArgsObjectMap);
                log.info("caching for {}: {}", fullMethodName, methodArgsObjectMap);
                return result;
            }
        }

        return invocation.proceed();
    }

    private String getFullMethodName(Method invocationMethod) {
        // fixme: для одного метода интерфейса в разных реализациях аннотация может присутствовать/отсутствовать - подумать над ключом
        Class<?> declaringClass = invocationMethod.getDeclaringClass();
        return String.format("%s.%s", declaringClass.getCanonicalName(), invocationMethod.getName());
    }

    private MethodArgs getMethodArgs(Object[] invocationArgs) {
        LinkedList<Object> args = new LinkedList<>();
        Collections.addAll(args, invocationArgs);
        return new MethodArgs(args);
    }

    @Data
    @AllArgsConstructor
    private static class MethodArgs {
        private LinkedList<Object> args;
    }
}
