package ru.practicum.shareit.request.model.dto;

import lombok.*;
import ru.practicum.shareit.user.model.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    private String description;
    private User requestor;
    private LocalDateTime created;
}