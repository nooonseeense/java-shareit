package ru.practicum.shareit.user.repository;


import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> get();

    Optional<User> get(Long id);

    User create(User user);

    User update(Long userId, UserDto userDto);

    void delete(Long id);
}
