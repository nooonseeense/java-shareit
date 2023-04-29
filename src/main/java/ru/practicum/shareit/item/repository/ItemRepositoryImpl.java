package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.entity.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private final Map<Long, List<Item>> userItemIndex = new LinkedHashMap<>();
    private Long itemId = 1L;

    @Override
    public List<Item> getAll(Long userId) {
        return userItemIndex.getOrDefault(userId, List.of());
    }

    @Override
    public Optional<Item> get(Long itemId) {
        return Optional.ofNullable(items.get(itemId));
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
        final List<Item> items = userItemIndex.computeIfAbsent(item.getOwner().getId(), k -> new ArrayList<>());
        items.add(item);
        log.debug("Вещь добавлена ID = {}", item.getId());
        return item;
    }
}