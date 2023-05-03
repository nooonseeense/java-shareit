package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public BookingDto get(@PositiveOrZero @PathVariable Long bookingId,
                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос GET: get(Long bookingId, Long userId) на получение данных о бронировании с ID = {}", bookingId);
        return bookingService.get(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> get(@RequestParam(defaultValue = "ALL", required = false) String state,
                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос GET: get(String state, Long userId) на получение списка всех бронирований пользователя с ID = {}", userId);
        return bookingService.get(state, userId);
    }

    @GetMapping("/owner")
    public List<BookingDto> getBookingsForAllOwnerItems(@RequestParam(defaultValue = "ALL", required = false) String state,
                                                        @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос GET: get(String state, Long userId) на получение списка бронирований для всех вещей текущего пользователя с ID = {}", userId);
        return bookingService.getBookingsForAllOwnerItems(state, userId);
    }

    @PostMapping
    public BookingDto add(@RequestBody @Valid BookingRequestDto bookingRequestDto,
                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос POST: add(BookingDto bookingDto, Long userId) на добавление бронирования.");
        return bookingService.add(bookingRequestDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approve(@PositiveOrZero @PathVariable Long bookingId,
                              @RequestParam Boolean isApproved,
                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос PATCH: approve(Long bookingId, Boolean isApproved, Long userId) на подтверждение или отклонения запроса на бронирование.");
        return bookingService.approve(bookingId, isApproved, userId);
    }
}