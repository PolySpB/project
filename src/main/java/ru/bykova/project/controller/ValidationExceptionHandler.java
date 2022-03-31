package ru.bykova.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bykova.project.model.ErrorDto;
import ru.bykova.project.utils.ErrorUtils;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDto handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("{}:: {}", e.getClass(), e.getMessage());
        return ErrorUtils.generate(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("{}:: {}", e.getClass(), e.getMessage());
        String errorMessage = e.getBindingResult().getAllErrors()
                .stream()
                .map(
                        objectError -> {
                            String field = ((FieldError) objectError).getField();
                            return field + ": " + objectError.getDefaultMessage();
                        })
                .collect(Collectors.joining(","));
        return ErrorUtils.generate(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
