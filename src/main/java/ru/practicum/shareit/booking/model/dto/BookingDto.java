package ru.practicum.shareit.booking.model.dto;

import lombok.*;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.user.model.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private Status status;
}