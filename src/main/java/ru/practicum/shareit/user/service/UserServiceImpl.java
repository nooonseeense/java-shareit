package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ObjectDoesNotExist;
import ru.practicum.shareit.exception.UserEmailValidationException;
import ru.practicum.shareit.user.model.entity.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<UserDto> get() {
        return userRepository.get()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto get(Long id) {
        User user = userRepository.get(id)
                .orElseThrow(() -> new ObjectDoesNotExist(String.format("Пользователь с ID %d не найден.", id)));
        return UserMapper.toUserDto(user);
    }

    public UserDto create(UserDto userDto) {
        validateEmail(userDto.getEmail(), true, null);
        return UserMapper.toUserDto(userRepository.create(UserMapper.toUser(userDto)));
    }

    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.get(id)
                .orElseThrow(() -> new ObjectDoesNotExist(String.format("Пользователь с ID %d не найден.", id)));
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            validateEmail(userDto.getEmail(), false, id);
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            user.setName(userDto.getName());
        }
        log.debug("Пользователь обновлен {}", user.getId());
        return UserMapper.toUserDto(user);
    }

    public void delete(Long id) {
        get(id);
        userRepository.delete(id);
    }

    public void validateEmail(String email, boolean isCreate, Long excludeUserId) {
        boolean emailExist = get().stream()
                .filter(u -> isCreate || !u.getId().equals(excludeUserId))
                .anyMatch(u -> u.getEmail().equals(email));
        if (emailExist) {
            String message = String.format("Такой email уже используется: '%s'", email);
            log.debug(message);
            throw new UserEmailValidationException(message);
        }
    }
}