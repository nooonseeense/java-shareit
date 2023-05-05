package ru.practicum.shareit.item.mapper.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.item.model.dto.item.ItemDto;

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

    public Item toItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }
}