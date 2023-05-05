package ru.practicum.shareit.item.model.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.entity.item.Item;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank
    private String text;
    private Item item;
    private String authorName;
    private LocalDateTime created;
}