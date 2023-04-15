package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.dto.ItemDto;
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
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> get(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос GET: get(Long userId) на получение списка вещей пользователя с ID {}.", userId);
        return itemService.get(userId);
    }

    @GetMapping("{itemId}")
    public ItemDto get(@RequestHeader("X-Sharer-User-Id") Long userId,
                       @PositiveOrZero @PathVariable Long itemId) {
        log.info("Запрос GET: get(Long userId, Long itemId) на получение вещи по ID = {}.", itemId);
        return itemService.get(userId, itemId);
    }

    @GetMapping("/search")
    public ItemDto search(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @RequestParam(name = "text") String text) {
        log.info("Запрос GET: search(Long userId, String text) на поиск вещи доступные для аренды, text = {}", text);
        return itemService.search(userId, text);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") Long userId,
                       @Valid @RequestBody ItemDto itemDto) {
        log.info("Запрос POST: add(Long userId, ItemDto itemDto) на добавление вещи пользователю с ID = {}", userId);
        return itemService.add(userId, itemDto);
    }

    @PatchMapping("{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @PositiveOrZero @PathVariable Long itemId,
                          @Valid @RequestBody ItemDto itemDto) {
        log.info("Запрос PATCH: update(Long userId, Long itemId, ItemDto itemDto) на изменение вещи пользователю с ID = {}", userId);
        return itemService.update(userId, itemId, itemDto);
    }
}