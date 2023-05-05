package ru.practicum.shareit.request.model.entity;

import java.time.LocalDateTime;

import lombok.*;
import ru.practicum.shareit.user.model.entity.User;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestor_id", referencedColumnName = "id")
    @ToString.Exclude
    private User requestor;
    private LocalDateTime created;
}