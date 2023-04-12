package ru.practicum.shareit.user.model;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private Long id;
    private String name;
    private String email;
}