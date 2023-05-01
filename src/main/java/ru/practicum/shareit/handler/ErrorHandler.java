package ru.practicum.shareit.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.user.exception.UserEmailValidationException;
import ru.practicum.shareit.user.exception.UserValidationException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserValidationException(final UserValidationException e) {
        String message = "Ошибка при валидации пользователя.";
        log.warn(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserEmailValidationException(final UserEmailValidationException e) {
        String message = "Ошибка при валидации email пользователя.";
        log.warn(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }

    @ExceptionHandler({ObjectDoesNotExist.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleObjectDoesNotExistException(final ObjectDoesNotExist e) {
        String message = "Запрашиваемый объект не найден.";
        log.warn(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleThrowableException(final Throwable e) {
        String message = "Сервер столкнулся с неожиданной ошибкой, которая помешала выполнить запрос.";
        log.warn(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        String message = "Ошибка при валидации данных.";
        log.warn(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }
}