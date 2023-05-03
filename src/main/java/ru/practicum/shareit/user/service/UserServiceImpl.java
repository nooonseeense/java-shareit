package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.handler.ObjectDoesNotExist;
import ru.practicum.shareit.user.exception.UserEmailValidationException;
import ru.practicum.shareit.user.model.entity.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> get() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto get(Long id) {
        return UserMapper.toUserDto(userRepository.findById(id)
                .orElseThrow(() -> new ObjectDoesNotExist(String.format("Пользователь с ID %d не найден.", id))));
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        //validateEmail(userDto.getEmail(), true, null);
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectDoesNotExist(String.format("Пользователь с ID %d не найден.", id)));
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            //validateEmail(userDto.getEmail(), false, id);
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            user.setName(userDto.getName());
        }
        log.debug("Пользователь обновлен {}", user.getId());
        return UserMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

//    public void validateEmail(String email, boolean isCreate, Long excludeUserId) {
//        boolean emailExist = get().stream()
//                .filter(u -> isCreate || !u.getId().equals(excludeUserId))
//                .anyMatch(u -> u.getEmail().equals(email));
//        if (emailExist) {
//            String message = String.format("Такой email уже используется: '%s'", email);
//            log.debug(message);
//            throw new UserEmailValidationException(message);
//        }
//    }
}