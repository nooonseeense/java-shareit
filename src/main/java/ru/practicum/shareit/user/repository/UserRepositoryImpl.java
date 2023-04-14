package ru.practicum.shareit.user.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.UserEmailValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private Long generatorId = 1L;

    @Override
    public List<User> get() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User get(Long id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new NoSuchElementException(String.format("Пользователь с ID %d не найден.", id))
        );
    }

    @Override
    public User create(UserDto userDto) {
        if (validationEmail(userDto)) {
            String message = String.format("Такой email уже используется: '%s'", userDto.getEmail());
            log.debug(message);
            throw new UserEmailValidationException(message);
        }
        User user = UserMapper.toUser(userDto);
        user.setId(generatorId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(Long id, UserDto userDto) {
        if (validationEmail(userDto)) {
            String message = String.format("Такой email уже используется: '%s'", userDto.getEmail());
            log.debug(message);
            throw new UserEmailValidationException(message);
        }
        User user = UserMapper.toUser(userDto);
        users.put(id, user);
        return user;
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    private boolean validationEmail(UserDto userDto) {
        return users.values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(userDto.getEmail()) && !Objects.equals(user.getId(), userDto.getId()));
    }
}