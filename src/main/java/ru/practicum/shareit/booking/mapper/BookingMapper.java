package ru.practicum.shareit.booking.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingShortDto;
import ru.practicum.shareit.booking.model.entity.Booking;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.user.model.entity.User;

@UtilityClass
public class BookingMapper {

    public BookingDto toBookingDto(Booking booking) {
        if (booking == null) {
            return null;
        }
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .status(booking.getStatus())
                .build();
    }

    public Booking toBooking(BookingRequestDto bookingRequestDto, Item item, User user) {
        return Booking.builder()
                .start(bookingRequestDto.getStart())
                .end(bookingRequestDto.getEnd())
                .item(item).booker(user)
                .status(Status.WAITING)
                .build();
    }

    public BookingShortDto toShortDto(Booking booking) {
        if (booking == null) {
            return null;
        }
        return BookingShortDto.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .build();
    }
}