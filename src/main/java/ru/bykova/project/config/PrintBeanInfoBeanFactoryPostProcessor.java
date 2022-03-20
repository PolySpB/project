package ru.bykova.project.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import ru.bykova.project.annotation.CacheResult;

import java.lang.reflect.Method;

@Slf4j
@Component
public class PrintBeanInfoBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    @SneakyThrows
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> beanClass = Class.forName(beanClassName);
            Method[] declaredMethods = beanClass.getDeclaredMethods();

            boolean isCacheResultAnnotationPresent = isCacheResultAnnotationPresent(declaredMethods);
            if (beanDefinition.isPrototype() && isCacheResultAnnotationPresent) {
                log.info("bean {} Scope == prototype && CacheResult annotation is present", beanDefinitionName);
            }
        }
    }

    private boolean isCacheResultAnnotationPresent(Method[] declaredMethods) {
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(CacheResult.class)) {
                return true;
            }
        }
        return false;
    }
}
