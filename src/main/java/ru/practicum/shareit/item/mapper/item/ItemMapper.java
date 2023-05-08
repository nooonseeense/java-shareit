package ru.practicum.shareit.item.mapper.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.dto.item.ItemShortDto;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.item.model.dto.item.ItemDto;
import ru.practicum.shareit.user.model.entity.User;

@UtilityClass
public class ItemMapper {

    public ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public Item toItem(ItemShortDto itemShortDto, User user) {
        return Item.builder()
                .id(itemShortDto.getId())
                .name(itemShortDto.getName())
                .description(itemShortDto.getDescription())
                .owner(user)
                .available(itemShortDto.getAvailable())
                .build();
    }

    public ItemShortDto toItemShortDto(Item item) {
        return ItemShortDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getId())
                .build();
    }
}