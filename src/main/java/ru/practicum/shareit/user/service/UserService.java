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
public class UserService {
    private final UserRepository userRepository;

    /**
     * Метод получения списка всех пользователей
     *
     * @return Список всех пользователей
     */
    public List<User> get() {
        return userRepository.get();
    }

    /**
     * Метод получения пользователя по ID
     *
     * @param id id пользователя
     * @return Объект пользователя
     */
    public UserDto get(Long id) {
        return UserMapper.toUserDto(userRepository.get(id));
    }

    /**
     * Метод создания пользователя
     *
     * @param userDto Принятый объект пользователя
     * @return созданный объект пользователя
     */
    public UserDto create(UserDto userDto) {

        return UserMapper.toUserDto(userRepository.create(userDto));
    }

    /**
     * Метод изменения пользователя
     *
     * @param userDto Принятый объект пользователя
     * @return изменённый объект пользователя
     */
    public UserDto update(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.update(userDto));
    }

    /**
     * Метод удаления пользователя по ID
     *
     * @param id id пользователя
     */
    public void delete(Long id) {
        if (userRepository.get(id) == null) {
            String message = String.format("Пользователь с ID %d не найден.", id);
            log.debug("delete(Long id): Пользователь с ID {} не найден.", id);
            throw new ObjectDoesNotExist(message);
        }
        userRepository.delete(id);
    }
}