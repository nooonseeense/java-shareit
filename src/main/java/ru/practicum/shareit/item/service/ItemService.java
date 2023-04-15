package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> get(Long userId);

    ItemDto get(Long userId, Long itemId);

    ItemDto search(Long userId, String text);

    ItemDto add(Long userId, ItemDto itemDto);

    ItemDto update(Long userId, Long itemId, ItemDto itemDto);
}