package ru.bykova.project.model;

import lombok.Builder;
import lombok.Data;
import ru.bykova.project.validation.Create;
import ru.bykova.project.validation.Email;
import ru.bykova.project.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
public class UserDto {
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Integer id;
    @NotBlank(message = "Can't be empty!")
    private String name;
    // Note: @Email from javax.validation.constraints.Email; can be used
    @Email
    private String email;
}
