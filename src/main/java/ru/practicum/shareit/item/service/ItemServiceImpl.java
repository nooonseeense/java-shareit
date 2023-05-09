package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.entity.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.mapper.comment.CommentMapper;
import ru.practicum.shareit.item.mapper.item.ItemMapper;
import ru.practicum.shareit.item.model.dto.comment.CommentDto;
import ru.practicum.shareit.item.model.dto.comment.CommentShortDto;
import ru.practicum.shareit.item.model.dto.item.ItemShortDto;
import ru.practicum.shareit.item.model.entity.comment.Comment;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.item.model.dto.item.ItemDto;
import ru.practicum.shareit.item.repository.comment.CommentRepository;
import ru.practicum.shareit.item.repository.item.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.entity.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private static final Sort SORT_CREATED_DESC = Sort.by(DESC, "created");
    private static final Sort SORT_START_DESC = Sort.by(DESC, "start");
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getAll(Long userId) {
        userService.get(userId);

        List<Item> items = itemRepository.findAllByOwnerIdOrderByIdAsc(userId);
        List<ItemDto> itemsDto = new ArrayList<>();

        Map<Item, List<Booking>> bookings = bookingRepository.findAllByItemInAndStatus(items, Status.APPROVED, SORT_START_DESC)
                .stream()
                .collect(Collectors.groupingBy(Booking::getItem));
        Map<Item, List<Comment>> comments = commentRepository.findAllByItemIdIn(items
                        .stream()
                        .map(Item::getId).collect(Collectors.toList()), SORT_CREATED_DESC)
                .stream()
                .collect(Collectors.groupingBy(Comment::getItem, Collectors.toList()));

        for (Item item : items) {
            ItemDto itemDto = ItemMapper.toItemDto(item);

            if (bookings.get(item) != null) {
                setBookings(itemDto, item);
            }
            if (comments.get(item) != null) {
                setComments(itemDto);
            }
            itemsDto.add(itemDto);
        }
        return itemsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto get(Long itemId, Long userId) {
        userService.get(userId);

        Item item = get(itemId);
        ItemDto itemDto = ItemMapper.toItemDto(item);

        setComments(itemDto);
        if (!item.getOwner().getId().equals(userId)) {
            return itemDto;
        }
        setBookings(itemDto, item);
        return itemDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Item get(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Вещь с ID = %d не найдена.", itemId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemShortDto> search(String text) {
        if (text.isBlank()) {
            return List.of();
        }
        return ItemMapper.toItemShortDto(itemRepository.findAllByNameOrDescriptionContainingIgnoreCaseAndAvailableTrue(text, text));
    }

    @Override
    @Transactional
    public ItemShortDto add(Long userId, ItemShortDto itemShortDto) {
        User user = UserMapper.toUser(userService.get(userId));
        Item item = ItemMapper.toItem(itemShortDto, user);
        itemRepository.save(item);
        return ItemMapper.toItemShortDto(item);
    }

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long itemId, CommentShortDto commentShortDto) {
        Item item = get(itemId);
        User user = UserMapper.toUser(userService.get(userId));

        if (!bookingRepository.existsAllByBookerIdAndItemIdAndStatusAndEndBefore(
                userId,
                itemId,
                Status.APPROVED,
                LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    String.format("Вещь с ID = %d не бралась пользователем с ID = %s в аренду.", itemId, userId));
        }
        Comment comment = CommentMapper.toComment(commentShortDto, item, user);
        commentRepository.save(comment);
        log.debug("Комментарий добавлен к вещи c ID = {}", item.getId());
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public ItemShortDto update(Long userId, Long itemId, ItemShortDto newItem) {
        userService.get(userId);
        Item item = get(itemId);
        if (!item.getOwner().getId().equals(userId)) {
            String message = String.format("Пользователь с ID = %d не владеет вещью с ID = %d", userId, itemId);
            log.debug(message);
            throw new NotFoundException(message);
        }
        if (newItem.getName() != null && !newItem.getName().isBlank()) {
            item.setName(newItem.getName());
        }
        if (newItem.getDescription() != null && !newItem.getDescription().isBlank()) {
            item.setDescription(newItem.getDescription());
        }
        if (newItem.getAvailable() != null) {
            item.setAvailable(newItem.getAvailable());
        }
        log.debug("Вещь обновлена {}", item.getId());
        return ItemMapper.toItemShortDto(itemRepository.save(item));
    }

    private void setBookings(ItemDto itemDto, Item item) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Pageable limitOne = PageRequest.of(0, 1);

        List<Booking> lastBookingList = bookingRepository.findLastBookingByItemAndStatus(
                item, Status.APPROVED, localDateTime, limitOne);
        List<Booking> nextBookingList = bookingRepository.findNextBookingByItemAndStatus(
                item, Status.APPROVED, localDateTime, limitOne);

        itemDto.setLastBooking(BookingMapper.toShortDto(lastBookingList.isEmpty() ? null : lastBookingList.get(0)));
        itemDto.setNextBooking(BookingMapper.toShortDto(nextBookingList.isEmpty() ? null : nextBookingList.get(0)));
    }

    private void setComments(ItemDto itemDto) {
        itemDto.setComments(commentRepository.findAllByItemIdIn(Collections.singletonList(itemDto.getId()), SORT_CREATED_DESC)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList()));
    }
}