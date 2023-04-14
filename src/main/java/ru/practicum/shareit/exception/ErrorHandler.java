package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    private static final String ERROR = "error";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserValidationException(final UserValidationException e) {
        log.warn("Ошибка при валидации пользователя.");
        return Map.of(ERROR, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserEmailValidationException(final UserEmailValidationException e) {
        log.warn("Ошибка при валидации email пользователя.");
        return Map.of(ERROR, e.getMessage());
    }

    @ExceptionHandler({ObjectDoesNotExist.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleObjectDoesNotExistException(final ObjectDoesNotExist e) {
        log.warn("Запрашиваемый объект не найден.");
        return Map.of(ERROR, e.getMessage());
    }
}