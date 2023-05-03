package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.enumeration.State;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.handler.ObjectDoesNotExist;
import ru.practicum.shareit.item.exception.ItemNotAvailableException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.entity.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.item.repository.item.ItemRepository;
import ru.practicum.shareit.user.model.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final static Sort SORT = Sort.by(Sort.Direction.DESC, "start");
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional(readOnly = true)
    public BookingDto get(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ObjectDoesNotExist("Бронирование найдено"));
        checkUserForDb(userId);
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new ObjectDoesNotExist("Бронирование не найдено");
        }
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> get(String state, Long userId) {
        checkUserForDb(userId);
        validState(state);
        switch (State.valueOf(state)) {
            case ALL:
                return bookingRepository.findAllByBookerId(userId, SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfter(userId, LocalDateTime.now(), LocalDateTime.now(), SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findAllByBookerIdAndEndIsBefore(userId, LocalDateTime.now(), SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findAllByBookerIdAndStartIsAfter(userId, LocalDateTime.now(), SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.findAllByBookerIdAndStatus(userId, Status.WAITING, SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.findAllByBookerIdAndStatus(userId, Status.REJECTED, SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsForAllOwnerItems(String state, Long userId) {
        checkUserForDb(userId);
        validState(state);
        switch (State.valueOf(state)) {
            case ALL:
                return bookingRepository.findAllByItemOwnerId(userId, SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfter(userId, LocalDateTime.now(), LocalDateTime.now(), SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findAllByItemOwnerIdAndEndIsBefore(userId, LocalDateTime.now(), SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findAllByItemOwnerIdAndStartIsAfter(userId, LocalDateTime.now(), SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.findAllByItemOwnerIdAndStatus(userId, Status.WAITING, SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.findAllByItemOwnerIdAndStatus(userId, Status.REJECTED, SORT)
                        .stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    @Transactional
    public BookingDto add(BookingRequestDto bookingRequestDto, Long userId) {
        if (bookingRequestDto.getStart().isAfter(bookingRequestDto.getEnd())) {
            throw new IllegalArgumentException("Дата окончания не может быть раньше даты начала бронирования.");
        }
        if (bookingRequestDto.getStart().equals(bookingRequestDto.getEnd())) {
            throw new IllegalArgumentException("Дата окончания не может быть равна даты начала бронирования.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ObjectDoesNotExist("Пользователь не найден."));
        Item item = itemRepository.findById(bookingRequestDto.getItemId()).orElseThrow(() -> new ItemNotFoundException("Вещь не найдена."));

        if (!item.getAvailable()) {
            throw new ItemNotAvailableException("Вещь недоступна для бронирования.");
        }
        Booking booking = bookingRepository.save(BookingMapper.toBooking(bookingRequestDto, item, user));
        log.debug("Бронирование вещи с ID = {} создано.", booking.getId());
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    @Transactional
    public BookingDto approve(Long bookingId, Boolean isApproved, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ObjectDoesNotExist("Бронирование найдено"));

        checkUserForDb(userId);
        if (!booking.getStatus().equals(Status.WAITING)) {
            throw new IllegalArgumentException("Бронирование уже существует.");
        }
        if (isApproved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        log.debug("Бронирование вещи с ID = {} подтверждено.", booking.getId());
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    private void checkUserForDb(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ObjectDoesNotExist("Пользователь не найден."));
    }

    private void validState(String state) {
        try {
            State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Такой статус не существует.");
        }
    }
}