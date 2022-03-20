package ru.bykova.project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import ru.bykova.project.annotation.CacheResult;

import java.lang.reflect.Method;

@Slf4j
@Component
public class CacheResultBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (AnnotationUtils.findAnnotation(method, CacheResult.class) != null) {
                log.info("bean {} method {} is annotated with CacheResult", beanName, method.getName());
                ProxyFactory proxyFactory = new ProxyFactory(bean);
                proxyFactory.addAdvice(new CacheResulMethodInterceptor());
                return proxyFactory.getProxy();
            }
        }
        return bean;
    }
}
