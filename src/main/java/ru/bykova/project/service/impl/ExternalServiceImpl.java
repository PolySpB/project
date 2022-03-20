package ru.bykova.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.bykova.project.annotation.CacheResult;
import ru.bykova.project.model.ExternalInfo;
import ru.bykova.project.service.ExternalService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Scope("prototype")
public class ExternalServiceImpl implements ExternalService {

    private static final Map<Integer, ExternalInfo> DATA_MAP = new HashMap<>();

    @Override
    @CacheResult
    public ExternalInfo getExternalInfo(Integer id) {
        log.info("getting externalInfo by id: {}", id);
        return DATA_MAP.get(id);
    }

    @PostConstruct
    private void initDataMap() {
        log.info("start constructing dataMap");
        DATA_MAP.put(1, new ExternalInfo(1, null));
        DATA_MAP.put(2, new ExternalInfo(2, "hasInfo"));
        DATA_MAP.put(3, new ExternalInfo(3, "info"));
        DATA_MAP.put(4, new ExternalInfo(4, "information"));
        log.info("dataMap: {}", DATA_MAP);
    }

    @PreDestroy
    private void destroy() {
        log.info("clearing dataMap: {}", DATA_MAP);
        DATA_MAP.clear();
        log.info("dataMap after clear: {}", DATA_MAP);
    }
}
