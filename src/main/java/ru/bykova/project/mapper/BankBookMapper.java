package ru.bykova.project.mapper;

import ru.bykova.project.model.entity.BankBookEntity;
import ru.bykova.project.model.entity.CurrencyEntity;
import ru.bykova.project.model.dto.BankBookDto;

public class BankBookMapper {

    public static BankBookDto entityToDto(BankBookEntity entity) {
        return BankBookDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .number(entity.getNumber())
                .amount(entity.getAmount())
                .currency(entity.getCurrency().getCurrency())
                .build();
    }

    public static BankBookEntity dtoToEntity(BankBookDto dto) {
        return BankBookEntity.builder()
                .id(dto.getId())
                .number(dto.getNumber())
                .amount(dto.getAmount())
                .currency(CurrencyEntity.builder()
                        .currency(dto.getCurrency())
                        .build())
                .build();
    }
}
