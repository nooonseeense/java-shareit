package ru.practicum.shareit.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

@Transactional(readOnly = true)
public interface UserService {
    List<UserDto> get();

    UserDto get(Long id);

    @Transactional
    UserDto create(UserDto userDto);

    @Transactional
    UserDto update(Long id, UserDto userDto);

    @Transactional
    void delete(Long id);
}
