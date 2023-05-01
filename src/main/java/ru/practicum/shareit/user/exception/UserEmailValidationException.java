package ru.practicum.shareit.user.exception;

public class UserEmailValidationException extends RuntimeException {
    public UserEmailValidationException(String message) {
        super(message);
    }
}
