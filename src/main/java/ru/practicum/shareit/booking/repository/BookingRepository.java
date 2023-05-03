package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.booking.model.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerId(Long bookerId, Sort sort);

    List<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Sort sort);

    List<Booking> findAllByBookerIdAndEndIsBefore(Long bookerId, LocalDateTime start, Sort sort);

    List<Booking> findAllByBookerIdAndStartIsAfter(Long bookerId, LocalDateTime start, Sort sort);

    List<Booking> findAllByBookerIdAndStatus(Long bookerId, Status status, Sort sort);

    List<Booking> findAllByItemOwnerId(Long bookerId, Sort sort);

    List<Booking> findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Sort sort);

    List<Booking> findAllByItemOwnerIdAndEndIsBefore(Long bookerId, LocalDateTime start, Sort sort);

    List<Booking> findAllByItemOwnerIdAndStartIsAfter(Long bookerId, LocalDateTime start, Sort sort);

    List<Booking> findAllByItemOwnerIdAndStatus(Long bookerId, Status status, Sort sort);
}