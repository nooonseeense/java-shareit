package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public List<ItemDto> get(Long userId) {
        return null;
    }

    @Override
    public ItemDto get(Long userId, Long itemId) {
        return null;
    }

    @Override
    public ItemDto search(Long userId, String text) {
        return null;
    }

    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        return null;
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto) {
        return null;
    }
}
