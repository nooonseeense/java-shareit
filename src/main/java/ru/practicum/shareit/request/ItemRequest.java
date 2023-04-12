package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemRequest {
    private Long id;
    private String description;
    private String requestor;
    private LocalDateTime created;
}
