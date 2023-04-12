package ru.practicum.shareit.user.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {
    private Long id;
    @NotBlank(message = "Имя не может быть пустым.")
    private String name;
    @Email(message = "Электронная почта не может быть пустой и должна содержать символ @.")
    @NotBlank
    private String email;
}