package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.practicum.shareit.handler.ObjectDoesNotExist;
import ru.practicum.shareit.item.mapper.item.ItemMapper;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.item.model.dto.item.ItemDto;
import ru.practicum.shareit.item.repository.item.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public List<ItemDto> getAll(Long userId) {
        return itemRepository.findById(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto get(Long itemId) {
        return ItemMapper.toItemDto(itemRepository.findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExist(String.format("Вещь с ID = %d не найдена", itemId))));
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isBlank()) {
            return List.of();
        }
        return itemRepository.findAllByNameOrDescriptionContainingIgnoreCaseAndAvailableTrue(text, text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(UserMapper.toUser(userService.get(userId)));
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto newItem) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExist(String.format("Пользователь с ID %d не найден.", userId)));
        if (!item.getOwner().getId().equals(userId)) {
            String message = String.format("Пользователь с ID = %d не владеет вещью с ID = %d", userId, itemId);
            log.debug(message);
            throw new ObjectDoesNotExist(message);
        }
        if (newItem.getName() != null && !newItem.getName().isBlank()) {
            item.setName(newItem.getName());
        }
        if (newItem.getDescription() != null && !newItem.getDescription().isBlank()) {
            item.setDescription(newItem.getDescription());
        }
        if (newItem.getAvailable() != null) {
            item.setAvailable(newItem.getAvailable());
        }
        log.debug("Вещь обновлена {}", item.getId());
        return ItemMapper.toItemDto(item);
    }
}