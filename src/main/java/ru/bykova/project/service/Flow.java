package ru.bykova.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bykova.project.model.ExternalInfo;

@Slf4j
@Component
public class Flow {
    ExternalService externalService;
    Process process;

    @Autowired
    public Flow(ExternalService externalService, @Lazy Process process) {
        this.externalService = externalService;
        this.process = process;
    }

    public void run(Integer id) {
        try {
            ExternalInfo externalInfo = externalService.getExternalInfo(id);
            if (externalInfo.getInfo() != null) {
                process.run(externalInfo);
            } else {
                log.info("externalInfo == null, externalInfo class: {}", externalInfo.getClass());
            }
        } catch (IllegalArgumentException e) {
            // this part is to show that AfterThrowing aspect is working
        }
    }
}
