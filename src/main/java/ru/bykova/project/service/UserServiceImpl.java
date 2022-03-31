package ru.bykova.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bykova.project.model.UserDto;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final List<UserDto> USER_STORAGE = new CopyOnWriteArrayList<>();
    private final AtomicInteger userSequenceId = new AtomicInteger(1);

    @PostConstruct
    public void initUsersStorage() {
        UserDto user1 = UserDto.builder()
                .id(userSequenceId.getAndIncrement())
                .name("Jane")
                .email("j.smith@gmail.com")
                .build();
        UserDto user2 = UserDto.builder()
                .id(userSequenceId.getAndIncrement())
                .name("Sam")
                .email("s.white@gmail.com")
                .build();
        Collections.addAll(USER_STORAGE, user1, user2);
    }

    @Override
    public List<UserDto> getAll() {
        return USER_STORAGE;
    }

    @Override
    public UserDto getById(Integer id) {
        Optional<UserDto> userDto = getUserById(id);

        if (userDto.isPresent()) {
            return userDto.get();
        } else {
            throw new NoSuchElementException(String.format("User with id=%s don'! exist!", id));
        }
    }

    @Override
    public UserDto create(UserDto userDto) {

        userDto.setId(userSequenceId.getAndIncrement());
        USER_STORAGE.add(userDto);

        return userDto;
    }

    @Override
    public UserDto update(UserDto userDto) {
        Optional<UserDto> userFromStorage = getUserById(userDto.getId());

        if (!userFromStorage.isPresent()) {
            String message = String.format("User with id=%s don't exists!", userDto.getId());
            throw new IllegalArgumentException(message);
        }

        UserDto user = userFromStorage.get();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        return user;
    }

    @Override
    public void deleteById(Integer id) {
        USER_STORAGE.removeIf(next -> next.getId().equals(id));
    }

    private Optional<UserDto> getUserById(Integer id) {
        return USER_STORAGE.stream().filter(s -> s.getId().equals(id)).findFirst();
    }
}
