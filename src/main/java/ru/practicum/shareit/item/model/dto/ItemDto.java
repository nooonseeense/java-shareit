package ru.practicum.shareit.item.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private boolean isAvailable;
    private Long requestId;
}
