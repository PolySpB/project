package ru.bykova.project.utils;

import org.springframework.http.HttpStatus;
import ru.bykova.project.model.ErrorDto;

public class ErrorUtils {

    public static ErrorDto generate(HttpStatus status, String errorMessage) {
        return ErrorDto.builder()
                .status(status)
                .errorMessage(errorMessage)
                .build();
    }
}
