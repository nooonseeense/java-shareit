package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> getAll(Long userId);

    Optional<Item> get(Long itemId);

    List<Item> search(String text);

    Item add(Item item);
}