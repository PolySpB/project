package ru.bykova.project.service;

import ru.bykova.project.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto getById(Integer id);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void deleteById(Integer id);
}