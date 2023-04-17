package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ObjectDoesNotExist;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private Long itemId = 1L;

    @Override
    public List<Item> getAll(Long userId) {
        return items.values()
                .stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Item get(Long itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> search(String text) {
        return items.values()
                .stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text) || item.getDescription().toLowerCase().contains(text))
                .collect(Collectors.toList());
    }

    @Override
    public Item add(Item item) {
        item.setId(itemId++);
        items.put(item.getId(), item);
        log.debug("Вещь добавлена {}", item);
        return item;
    }

    @Override
    public Item update(Item item) {
        Item newItem = get(item.getId());
        if (isOwner(newItem, item.getOwner().getId())) {
            String message = String.format("Пользователь с ID = %d не владелец ITEM = %s", item.getOwner().getId(), item.getName());
            log.debug(message);
            throw new ObjectDoesNotExist(message);
        }
        newItem.setName(Objects.requireNonNullElse(item.getName(), newItem.getName()));
        newItem.setDescription(Objects.requireNonNullElse(item.getDescription(), newItem.getDescription()));
        if (item.getAvailable() != null) {
            newItem.setAvailable(item.getAvailable());
        }
        items.put(newItem.getId(), newItem);
        return newItem;
    }

    private boolean isOwner(Item item, Long userId) {
        return item == null || !item.getOwner().getId().equals(userId);
    }
}