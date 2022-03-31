package ru.bykova.project.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorDto {
    private HttpStatus status;
    private String errorMessage;
}
