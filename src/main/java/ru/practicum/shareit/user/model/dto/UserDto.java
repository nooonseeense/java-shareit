package ru.practicum.shareit.user.model.dto;

import lombok.*;
import ru.practicum.shareit.user.controller.marker.Marker;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(groups = {Marker.OnCreate.class})
    private String name;
    @Email(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @NotEmpty(groups = {Marker.OnCreate.class})
    private String email;
}