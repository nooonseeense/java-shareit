package ru.practicum.shareit.item.model.dto.comment;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentShortDto {
    @NotBlank
    private String text;
}