package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> getAll(Long userId);

    Optional<Item> get(Long itemId);

    List<Item> search(String text);

    Item add(Item item);

    Item update(Item oldItem, ItemDto itemDto);
}