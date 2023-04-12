package ru.practicum.shareit.user.repository;


import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

public interface UserRepository {

    List<User> get();

    User get(Long id);

    User create(UserDto userDto);

    User update(UserDto userDto);

    void delete(Long id);
}
