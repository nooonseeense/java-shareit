package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAll(Long userId);

    ItemDto get(Long itemId);

    List<ItemDto> search(String text);

    ItemDto add(Long userId, ItemDto itemDto);

    ItemDto update(Long userId, Long itemId, ItemDto itemDto);
}