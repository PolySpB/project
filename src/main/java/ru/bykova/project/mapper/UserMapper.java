package ru.bykova.project.mapper;

import ru.bykova.project.entity.UserEntity;
import ru.bykova.project.model.UserDto;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto entityToDto(UserEntity entity) {
        String collect = entity.getBankbooks()
                .stream()
                .map(s -> String.format("Bankbook number=%s amount=%s %s", s.getNumber(), s.getAmount(), s.getCurrency().getCurrency()))
                .collect(Collectors.joining(","));

        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .bankBooksInfo(collect)
                .build();
    }


    public static UserEntity dtoToEntity(UserDto dto) {
        return UserEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}
