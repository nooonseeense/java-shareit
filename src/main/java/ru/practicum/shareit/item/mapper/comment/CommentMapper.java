package ru.practicum.shareit.item.mapper.comment;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.dto.comment.CommentDto;
import ru.practicum.shareit.item.model.dto.comment.CommentShortDto;
import ru.practicum.shareit.item.model.entity.comment.Comment;
import ru.practicum.shareit.item.model.entity.item.Item;
import ru.practicum.shareit.user.model.entity.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public Comment toComment(CommentShortDto commentShortDto, Item item, User user) {
        return Comment.builder()
                .text(commentShortDto.getText())
                .item(item)
                .author(user)
                .created(LocalDateTime.now())
                .build();
    }
}