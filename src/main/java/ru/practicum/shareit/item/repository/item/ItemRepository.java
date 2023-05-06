package ru.practicum.shareit.item.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.entity.item.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByOwnerIdOrderByIdAsc(Long userId);

    List<Item> findAllByNameOrDescriptionContainingIgnoreCaseAndAvailableTrue(String name, String description);

}