package ru.bykova.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bykova.project.entity.UserEntity;
import ru.bykova.project.mapper.UserMapper;
import ru.bykova.project.model.UserDto;
import ru.bykova.project.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Integer id) {

        Optional<UserEntity> userById = userRepository.findById(id);

        if (userById.isPresent()) {
            return UserMapper.entityToDto(userById.get());
        } else {
            throw new NoSuchElementException(String.format("User with id=%s don'! exist!", id));
        }
    }

    @Override
    public UserDto create(UserDto userDto) {

        UserEntity userEntity = UserMapper.dtoToEntity(userDto);
        userRepository.save(userEntity);

        return UserMapper.entityToDto(userEntity);
    }

    @Override
    public UserDto update(UserDto userDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userDto.getId());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            userEntity.setName(userDto.getName());
            userEntity.setEmail(userDto.getEmail());

            userRepository.save(userEntity);

            return UserMapper.entityToDto(userEntity);
        }

        throw new IllegalArgumentException(String.format("User with id=%s don't exist!", userDto.getId()));
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}