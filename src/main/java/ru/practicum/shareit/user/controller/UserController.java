package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.controller.marker.Marker;

import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> get() {
        log.info("Запрос GET: get() на получение списка всех пользователей.");
        return userService.get();
    }

    @GetMapping("{id}")
    public UserDto get(@PositiveOrZero @PathVariable Long id) {
        log.info("Запрос GET: get(Long id) на получение пользователя по ID = {}.", id);
        return userService.get(id);
    }


    @PostMapping()
    public UserDto create(@RequestBody @Validated(Marker.OnCreate.class) UserDto userDto) {
        log.info("Запрос POST: create(UserDto userDto) на создание пользователя.");
        return userService.create(userDto);
    }


    @PatchMapping("{id}")
    public UserDto update(@PathVariable Long id,
                          @Validated(Marker.OnUpdate.class) @RequestBody UserDto userDto) {
        log.info("Запрос PATCH: update(Long id, UserDto userDto) на изменение пользователя.");
        return userService.update(id, userDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PositiveOrZero @PathVariable Long id) {
        log.info("Запрос DELETE: delete(Long id) на удаление пользователя с ID = {}.", id);
        userService.delete(id);
    }
}