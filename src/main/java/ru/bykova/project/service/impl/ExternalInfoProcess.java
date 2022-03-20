package ru.bykova.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.bykova.project.model.ExternalInfo;
import ru.bykova.project.service.Process;

import java.util.Objects;

@Slf4j
@Lazy
@Service
public class ExternalInfoProcess implements Process {

    @Value("${id-not-process}")
    private Integer id;

    @Override
    public boolean run(ExternalInfo externalInfo) {
        boolean result = Objects.equals(this.id, externalInfo.getId());
        log.info("id-not-process == externalInfoId -> {}", result);
        return result;
    }
}
