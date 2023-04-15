package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ObjectDoesNotExist;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> get() {
        return userRepository.get();
    }

    public UserDto get(Long id) {
        return UserMapper.toUserDto(userRepository.get(id));
    }

    public UserDto create(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.create(userDto));
    }

    public UserDto update(Long id, UserDto userDto) {
        return UserMapper.toUserDto(userRepository.update(id, userDto));
    }

    public void delete(Long id) {
        if (userRepository.get(id) == null) {
            String message = String.format("Пользователь с ID %d не найден.", id);
            log.debug("delete(Long id): Пользователь с ID {} не найден.", id);
            throw new ObjectDoesNotExist(message);
        }
        userRepository.delete(id);
    }
}