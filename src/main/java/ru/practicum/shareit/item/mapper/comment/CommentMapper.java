package ru.practicum.shareit.item.mapper.comment;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.dto.comment.CommentDto;
import ru.practicum.shareit.item.model.entity.comment.Comment;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.user.model.entity.User;

@UtilityClass
public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .item(comment.getItem())
                .author(comment.getAuthor())
                .created(comment.getCreated())
                .build();
    }

    public static Comment toComment(CommentDto commentDto, Item item, User user) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .item(item)
                .author(user)
                .created(commentDto.getCreated())
                .build();
    }
}