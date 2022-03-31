package ru.bykova.project.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
public class TransactionDto {

    @NotBlank
    private String sourceBankBookNumber;

    @NotBlank
    private String targetBankBookNumber;

    @NotBlank
    private BigDecimal amount;

}
