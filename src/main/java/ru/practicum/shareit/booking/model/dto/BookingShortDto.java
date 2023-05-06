package ru.practicum.shareit.booking.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingShortDto {
    private Long id;
    private Long bookerId;
}