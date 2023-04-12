package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private Long generatorId = 1L;

    @Override
    public List<User> get() {
        return List.copyOf(users.values());
    }

    @Override
    public User get(Long id) {
        return users.get(id);
    }

    @Override
    public User create(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        user.setId(generatorId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }
}