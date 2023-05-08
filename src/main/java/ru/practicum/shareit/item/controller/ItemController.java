package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.dto.comment.CommentDto;
import ru.practicum.shareit.item.model.dto.comment.CommentShortDto;
import ru.practicum.shareit.item.model.dto.item.ItemDto;
import ru.practicum.shareit.item.model.dto.item.ItemShortDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {
    private static final String HEADER_USER = "X-Sharer-User-Id";
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(HEADER_USER) Long userId) {
        log.info("Запрос GET: getAll(Long userId) на получение списка вещей пользователя с ID {}.", userId);
        return itemService.getAll(userId);
    }

    @GetMapping("{itemId}")
    public ItemDto get(@PositiveOrZero @PathVariable Long itemId,
                       @RequestHeader(HEADER_USER) Long userId) {
        log.info("Запрос GET: get(Long itemId) на получение вещи по ID = {}.", itemId);
        return itemService.get(itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemShortDto> search(@RequestParam String text) {
        log.info("Запрос GET: search(String text) на поиск вещи доступные для аренды, text = {}", text);
        return itemService.search(text);
    }

    @PostMapping
    public ItemShortDto add(@RequestHeader(HEADER_USER) Long userId,
                            @Valid @RequestBody ItemShortDto itemShortDto) {
        log.info("Запрос POST: add(Long userId, ItemDto itemDto) на добавление вещи пользователю с ID = {}", userId);
        return itemService.add(userId, itemShortDto);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(HEADER_USER) Long userId,
                                 @PathVariable Long itemId,
                                 @Valid @RequestBody CommentShortDto commentShortDto) {
        log.info("Запрос POST: addComment(Long userId, Long itemId) на добавление комментария к вещи по ID = {}", itemId);
        return itemService.addComment(userId, itemId, commentShortDto);
    }

    @PatchMapping("{itemId}")
    public ItemShortDto update(@RequestHeader(HEADER_USER) Long userId,
                          @PositiveOrZero @PathVariable Long itemId,
                          @RequestBody ItemShortDto itemShortDto) {
        log.info("Запрос PATCH: update(Long userId, Long itemId, ItemDto itemDto) на изменение вещи пользователю с ID = {}", userId);
        return itemService.update(userId, itemId, itemShortDto);
    }
}