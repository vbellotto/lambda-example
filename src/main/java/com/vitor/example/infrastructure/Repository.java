package com.vitor.example.infrastructure;

import com.vitor.example.domain.entity.Item;

import java.util.List;
import java.util.Optional;

/**
 * Define the Repository operations.
 *
 */
public interface Repository {

    /**
     * Get all items.
     * @return - List of {@link Item}
     */
    List<Item> findAll();

    /**
     * Get an item by id.
     * @param id - the item id
     * @return - {@link Optional} of {@link Item}
     */
    Optional<Item> findById(Long id);

    /**
     * Delete an item by its Id.
     * @param id - the item id
     */
    void deleteById(Long id);

    /**
     * Insert a new item.
     * @param item - the {@link Item}
     */
    void save(Item item);
}
