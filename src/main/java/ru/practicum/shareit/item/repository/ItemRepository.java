package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getAll(Long userId);

    Item get(Long itemId);

    List<Item> search(String text);

    Item add(Item item);

    Item update(Item itemDto);
}