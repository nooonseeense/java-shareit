package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.booking.model.entity.Booking;
import ru.practicum.shareit.item.model.entity.item.Item;

import java.time.LocalDateTime;
import java.util.List;

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

    boolean existsAllByBookerIdAndItemIdAndStatusAndEndBefore(Long bookerId, Long itemId, Status status, LocalDateTime end);

    List<Booking> findAllByItemInAndStatus(List<Item> items, Status status, Sort sort);

    @Query("SELECT b FROM Booking b WHERE b.item = :item AND b.status = :status AND b.start < :localDateTime ORDER BY b.end DESC")
    List<Booking> findLastBookingByItemAndStatus(@Param("item") Item item,
                                                 @Param("status") Status status,
                                                 @Param("localDateTime") LocalDateTime localDateTime,
                                                 Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.item = :item AND b.status = :status AND b.start > :localDateTime ORDER BY b.end ASC")
    List<Booking> findNextBookingByItemAndStatus(@Param("item") Item item,
                                                 @Param("status") Status status,
                                                 @Param("localDateTime") LocalDateTime localDateTime,
                                                 Pageable pageable);

}