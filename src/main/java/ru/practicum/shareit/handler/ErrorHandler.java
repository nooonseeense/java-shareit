package ru.practicum.shareit.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.user.exception.UserEmailValidationException;
import ru.practicum.shareit.user.exception.UserValidationException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflictException(final UserEmailValidationException e) {
        String message = "Запрос не может быть выполнен из-за конфликтного обращения к ресурсу";
        log.error(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }

    @ExceptionHandler({ObjectDoesNotExist.class, ItemNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final ObjectDoesNotExist e) {
        String message = "Запрашиваемый объект не найден.";
        log.error(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleThrowableException(final Throwable e) {
        String message = "Сервер столкнулся с неожиданной ошибкой, которая помешала выполнить запрос.";
        log.error(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, UserValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestException(final Exception e) {
        String message = "Отправлен неккоректный запрос серверу.";
        log.error(message, e.getMessage());
        return Map.of(message, e.getMessage());
    }
}