package ru.practicum.shareit.booking.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.booking.model.dto.BookingFullResponseDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.dto.BookingShortDto;
import ru.practicum.shareit.booking.model.entity.Booking;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.user.model.entity.User;

@UtilityClass
public class BookingMapper {

    public BookingFullResponseDto toBookingDto(Booking booking) {
        if (booking == null) {
            return null;
        }
        return BookingFullResponseDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(toItemDto(booking.getItem()))
                .booker(toUserDto(booking.getBooker()))
                .status(booking.getStatus())
                .build();
    }

    public Booking toBooking(BookingRequestDto bookingRequestDto, Item item, User user) {
        return Booking.builder()
                .start(bookingRequestDto.getStart())
                .end(bookingRequestDto.getEnd())
                .item(item)
                .booker(user)
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

    private static BookingFullResponseDto.Item toItemDto(Item item) {
        return new BookingFullResponseDto.Item(item.getId(), item.getName());
    }

    private static BookingFullResponseDto.User toUserDto(User user) {
        return new BookingFullResponseDto.User(user.getId(), user.getName());
    }
}