package ru.practicum.shareit.item.model.dto.item;

import lombok.*;
import ru.practicum.shareit.booking.model.dto.BookingShortDto;
import ru.practicum.shareit.item.model.dto.comment.CommentDto;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingShortDto lastBooking;
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;
}