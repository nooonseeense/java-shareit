package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.entity.User;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private Long userId = 1L;

    @Override
    public List<User> get() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User create(User user) {
        user.setId(userId++);
        users.put(user.getId(), user);
        log.debug("Пользователь создан {}", user.getId());
        return user;
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
        log.debug("Пользователь с ID = {} удален", id);
    }
}