package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ObjectDoesNotExist;
import ru.practicum.shareit.exception.UserEmailValidationException;
import ru.practicum.shareit.user.model.User;

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
    public User get(Long id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new ObjectDoesNotExist(String.format("Пользователь с ID %d не найден.", id))
        );
    }

    @Override
    public User create(User user) {
        if (validationEmail(user)) {
            String message = String.format("Такой email уже используется: '%s'", user.getEmail());
            log.debug(message);
            throw new UserEmailValidationException(message);
        }
        user.setId(userId++);
        users.put(user.getId(), user);
        log.debug("Пользователь создан {}", user);
        return user;
    }

    @Override
    public User update(User user) {
        if (validationEmail(user)) {
            String message = String.format("Такой email уже используется: '%s'", user.getEmail());
            log.debug(message);
            throw new UserEmailValidationException(message);
        }
        users.put(user.getId(), user);
        log.debug("Пользователь обновлен {}", user);
        return user;
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
        log.debug("Пользователь с ID = {} удален", id);
    }

    private boolean validationEmail(User user) {
        return users.values()
                .stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()) && !Objects.equals(u.getId(), user.getId()));
    }
}