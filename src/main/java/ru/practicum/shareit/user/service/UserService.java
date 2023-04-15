package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Метод получения списка всех пользователей
     *
     * @return Список всех пользователей
     */
    List<User> get();

    /**
     * Метод получения пользователя по ID
     *
     * @param id id пользователя
     * @return Объект пользователя
     */
    UserDto get(Long id);

    /**
     * Метод создания пользователя
     *
     * @param userDto Принятый объект пользователя
     * @return созданный объект пользователя
     */
    UserDto create(UserDto userDto);

    /**
     * Метод изменения пользователя
     *
     * @param userDto Принятый объект пользователя
     * @return изменённый объект пользователя
     */
    UserDto update(Long id, UserDto userDto);

    /**
     * Метод удаления пользователя по ID
     *
     * @param id id пользователя
     */
    void delete(Long id);
}
