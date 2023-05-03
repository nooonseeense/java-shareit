package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;

import java.util.List;

public interface BookingService {

    BookingDto get(Long bookingId, Long userId);

    List<BookingDto> get(String state, Long userId);

    List<BookingDto> getBookingsForAllOwnerItems(String state, Long userId);

    BookingDto add(BookingRequestDto bookingRequestDto, Long userId);

    BookingDto approve(Long bookingId, Boolean isApproved, Long userId);
}
