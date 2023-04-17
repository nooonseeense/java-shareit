package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public List<User> get() {
        log.info("Запрос GET: get() на получение списка всех пользователей.");
        return userServiceImpl.get();
    }

    @GetMapping("{id}")
    public UserDto get(@PositiveOrZero @PathVariable Long id) {
        log.info("Запрос GET: get(Long id) на получение пользователя по ID = {}.", id);
        return userServiceImpl.get(id);
    }

    @PostMapping()
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("Запрос POST: create(UserDto userDto) на создание пользователя.");
        return userServiceImpl.create(userDto);
    }

    @PatchMapping("{id}")
    public UserDto update(@PathVariable Long id,
                          @RequestBody UserDto userDto) {
        log.info("Запрос PATCH: update(Long id, UserDto userDto) на изменение пользователя.");
        return userServiceImpl.update(id, userDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PositiveOrZero @PathVariable Long id) {
        log.info("Запрос DELETE: delete(Long id) на удаление пользователя с ID = {}.", id);
        userServiceImpl.delete(id);
    }
}