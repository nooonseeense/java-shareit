package ru.practicum.shareit.booking.model;

import lombok.*;
import ru.practicum.shareit.booking.enumeration.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Booking {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String item;
    private String booker;
    private Status status;
}
