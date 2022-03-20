package ru.bykova.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.bykova.project.annotation.CheckRequest;
import ru.bykova.project.model.ExternalInfo;

import java.util.Objects;

import static java.lang.Boolean.FALSE;

@Slf4j
@Lazy
@Service
public class ExternalInfoProcess implements Process {

    @Value("${id-not-process}")
    private Integer id;

    @Override
    @CheckRequest
    public Boolean run(ExternalInfo externalInfo) {
        boolean result = Objects.equals(this.id, externalInfo.getId());

        if (result == FALSE) {
            throw new IllegalArgumentException("externalInfoId is null");
        } else {
            log.info("id-not-process == externalInfoId -> {}", result);
        }

        return result;
    }
}
