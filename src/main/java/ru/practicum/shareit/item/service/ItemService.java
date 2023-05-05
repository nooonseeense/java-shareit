package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.comment.CommentDto;
import ru.practicum.shareit.item.model.dto.item.ItemDto;
import ru.practicum.shareit.item.model.entity.item.Item;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAll(Long userId);

    ItemDto get(Long itemId, Long userId);

    Item get(Long itemId);

    List<ItemDto> search(String text);

    ItemDto add(Long userId, ItemDto itemDto);

    ItemDto update(Long userId, Long itemId, ItemDto itemDto);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);
}