package ru.practicum.shareit.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.model.dto.item.ItemDto;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {

    List<ItemDto> getAll(Long userId);

    ItemDto get(Long itemId);

    List<ItemDto> search(String text);

    @Transactional
    ItemDto add(Long userId, ItemDto itemDto);

    @Transactional
    ItemDto update(Long userId, Long itemId, ItemDto itemDto);
}