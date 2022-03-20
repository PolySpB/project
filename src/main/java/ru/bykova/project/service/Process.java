package ru.bykova.project.service;

import ru.bykova.project.model.ExternalInfo;

public interface Process {
    Boolean run(ExternalInfo externalInfo);
}
