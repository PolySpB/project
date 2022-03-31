package ru.bykova.project.utils;

import ru.bykova.project.model.ErrorDto;

public class ErrorUtils {
    public static final String NOT_FOUND = "NOT_FOUND";

    public static ErrorDto generate(String errorMessage) {
        return ErrorDto.builder()
                .errorMessage(errorMessage)
                .build();
    }
}
