package ru.bykova.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bykova.project.model.UserDto;
import ru.bykova.project.service.UserService;
import ru.bykova.project.utils.ErrorUtils;
import ru.bykova.project.validation.Create;
import ru.bykova.project.validation.Update;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/all")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/user/by-id/{id}")
    public ResponseEntity getById(@NotNull @PathVariable Integer id) {
        try {
            UserDto userDto = userService.getById(id);
            return ResponseEntity.ok().body(userDto);
        } catch (Exception e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @PostMapping("/user")
    @Validated(Create.class)
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        log.info("userDto={}", userDto);
        UserDto user = userService.create(userDto);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/user")
    @Validated(Update.class)
    public ResponseEntity update(@Valid @RequestBody UserDto userDto) {
        log.info("userDto={}", userDto);
        try {
            UserDto user = userService.update(userDto);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteById(@NotNull @PathVariable Integer id) {
        userService.deleteById(id);
        String message = String.format("User with id=%s successfully deleted!", id);
        return ResponseEntity.ok().body(message);
    }
}
