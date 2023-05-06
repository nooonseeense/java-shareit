package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> get();

    UserDto get(Long id);

    UserDto create(UserDto userDto);

    UserDto update(Long id, UserDto userDto);

    void delete(Long id);

}
