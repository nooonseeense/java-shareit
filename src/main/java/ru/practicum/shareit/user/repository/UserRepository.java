package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> get();

    Optional<User> get(Long id);

    User create(User user);

    void delete(Long id);
}
