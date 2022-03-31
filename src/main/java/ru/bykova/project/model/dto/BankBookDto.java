package ru.bykova.project.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.bykova.project.validation.Create;
import ru.bykova.project.validation.Update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

@Data
@Builder
public class BankBookDto {
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Integer id;

    private Integer userId;

    @NotBlank(message = "Not blank!")
    private String number;

    @Min(value = 0L)
    private BigDecimal amount;

    private String currency;
}
