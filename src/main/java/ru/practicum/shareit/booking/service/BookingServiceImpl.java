package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.enumeration.State;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.error.exception.BadRequestException;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.dto.BookingFullResponseDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.entity.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.entity.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final Sort sort = Sort.by(Sort.Direction.DESC, "start");
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    @Transactional(readOnly = true)
    public BookingFullResponseDto get(Long bookingId, Long userId) {
        Booking booking = get(bookingId);

        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotFoundException("Бронирование не найдено");
        }
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingFullResponseDto> get(String state, Long userId) {
        userService.get(userId);
        validState(state);
        switch (State.valueOf(state)) {
            case ALL:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByBookerId(userId, sort));
            case CURRENT:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfter
                        (userId, LocalDateTime.now(), LocalDateTime.now(), sort));
            case PAST:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByBookerIdAndEndIsBefore
                        (userId, LocalDateTime.now(), sort));
            case FUTURE:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByBookerIdAndStartIsAfter
                        (userId, LocalDateTime.now(), sort));
            case WAITING:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByBookerIdAndStatus
                        (userId, Status.WAITING, sort));
            case REJECTED:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByBookerIdAndStatus
                        (userId, Status.REJECTED, sort));
        }
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingFullResponseDto> getBookingsForAllOwnerItems(String state, Long userId) {
        userService.get(userId);
        validState(state);
        switch (State.valueOf(state)) {
            case ALL:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByItemOwnerId(userId, sort));
            case CURRENT:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfter
                        (userId, LocalDateTime.now(), LocalDateTime.now(), sort));
            case PAST:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByItemOwnerIdAndEndIsBefore
                        (userId, LocalDateTime.now(), sort));
            case FUTURE:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByItemOwnerIdAndStartIsAfter
                        (userId, LocalDateTime.now(), sort));
            case WAITING:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByItemOwnerIdAndStatus
                        (userId, Status.WAITING, sort));
            case REJECTED:
                return BookingMapper.toBookingFullResponseDto(bookingRepository.findAllByItemOwnerIdAndStatus
                        (userId, Status.REJECTED, sort));
        }
        return List.of();
    }

    @Override
    @Transactional
    public BookingFullResponseDto add(BookingRequestDto bookingRequestDto, Long userId) {
        if (bookingRequestDto.getStart().isAfter(bookingRequestDto.getEnd())) {
            throw new IllegalArgumentException("Дата окончания не может быть раньше даты начала бронирования.");
        }
        if (bookingRequestDto.getStart().equals(bookingRequestDto.getEnd())) {
            throw new IllegalArgumentException("Дата окончания не может быть равна даты начала бронирования.");
        }
        User user = UserMapper.toUser(userService.get(userId));
        Item item = itemService.get(bookingRequestDto.getItemId());

        if (user.getId().equals(item.getOwner().getId())) {
            throw new NotFoundException("Вещь недоступна для бронирования.");
        }
        if (!item.getAvailable()) {
            throw new BadRequestException("Вещь недоступна для бронирования.");
        }
        Booking booking = bookingRepository.save(BookingMapper.toBooking(bookingRequestDto, item, user));
        log.debug("Бронирование вещи с ID = {} создано.", booking.getId());
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    @Transactional
    public BookingFullResponseDto approve(Long bookingId, Boolean approved, Long userId) {
        Booking booking = get(bookingId);
        userService.get(userId);

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotFoundException("Бронирование не найдено.");
        }
        if (!booking.getStatus().equals(Status.WAITING)) {
            throw new BadRequestException("Бронирование уже существует.");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        log.debug("Бронирование вещи с ID = {} подтверждено.", booking.getId());
        return BookingMapper.toBookingDto(booking);
    }

    private void validState(String state) {
        try {
            State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    private Booking get(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирование найдено"));
    }
}