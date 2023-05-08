package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.dto.BookingFullResponseDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;

import java.util.List;

public interface BookingService {

    BookingFullResponseDto get(Long bookingId, Long userId);

    List<BookingFullResponseDto> get(String state, Long userId);

    List<BookingFullResponseDto> getBookingsForAllOwnerItems(String state, Long userId);

    BookingFullResponseDto add(BookingRequestDto bookingRequestDto, Long userId);

    BookingFullResponseDto approve(Long bookingId, Boolean isApproved, Long userId);
}
