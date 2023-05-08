package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.comment.CommentDto;
import ru.practicum.shareit.item.model.dto.item.ItemDto;
import ru.practicum.shareit.item.model.dto.item.ItemShortDto;
import ru.practicum.shareit.item.model.entity.item.Item;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAll(Long userId);

    ItemDto get(Long itemId, Long userId);

    Item get(Long itemId);

    List<ItemShortDto> search(String text);

    ItemShortDto add(Long userId, ItemShortDto itemShortDto);

    ItemShortDto update(Long userId, Long itemId, ItemShortDto itemShortDto);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);

}