package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;

    @Override
    public List<ItemDto> get(Long userId) {
        return itemRepository.get(userId);
    }

    @Override
    public ItemDto get(Long userId, Long itemId) {
        return itemRepository.get(userId, itemId);
    }

    @Override
    public ItemDto search(Long userId, String text) {
        return itemRepository.search(userId, text);
    }

    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        return itemRepository.add(userId, itemDto);
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto) {
        return itemRepository.update(userId, itemId, itemDto);
    }
}