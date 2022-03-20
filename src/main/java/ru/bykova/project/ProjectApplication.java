package ru.bykova.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.bykova.project.service.impl.Flow;

@Slf4j
@ComponentScan
@PropertySource("classpath:application.properties")
public class ProjectApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectApplication.class);
        Flow flow = context.getBean(Flow.class);
        flow.run(1);
        flow.run(2);
        flow.run(3);
        flow.run(4);
    }
}
