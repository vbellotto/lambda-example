package com.vitor.example.domain.service;

import com.vitor.example.domain.exception.ItemNotFoundException;
import com.vitor.example.domain.model.ItemSummary;

import java.util.List;

/**
 * The Item Service interface.
 *
 */
public interface ItemService {

    /**
     * Return a list of {@link ItemSummary}.
     *
     * @return - list of {@link ItemSummary}
     */
    List<ItemSummary> findAll();

    /**
     * Return a single {@link ItemSummary}.
     *
     * @param id - the item id.
     * @return - {@link ItemSummary}
     * @throws ItemNotFoundException - the item not found exception
     */
    ItemSummary findById(Long id) throws ItemNotFoundException;

    /**
     * Delete one item.
     * @param id - the item id
     */
    void deleteById(Long id);

    /**
     * Insert a {@link ItemSummary}.
     * @param item - The {@link ItemSummary} to insert.
     * @return - the inserted {@link ItemSummary}
     */
    ItemSummary insert(ItemSummary item);
}
