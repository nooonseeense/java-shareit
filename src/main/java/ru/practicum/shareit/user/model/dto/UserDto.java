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
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
}