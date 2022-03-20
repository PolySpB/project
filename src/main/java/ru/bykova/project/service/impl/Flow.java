package ru.bykova.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.bykova.project.model.ExternalInfo;
import ru.bykova.project.service.ExternalService;
import ru.bykova.project.service.Process;

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
        ExternalInfo externalInfo = externalService.getExternalInfo(id);

        if (externalInfo.getInfo() != null) {
            process.run(externalInfo);
        } else {
            log.info("externalInfo == null, externalInfo class: {}", externalInfo.getClass());
        }
    }
}
