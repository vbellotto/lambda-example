package com.vitor.example.domain.repository.impl;

import com.vitor.example.domain.entity.Item;
import com.vitor.example.domain.repository.ItemRepository;
import com.vitor.example.infrastructure.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@link ItemRepository} implementation.
 *
 */
@Singleton
public class ItemRepositoryImpl implements ItemRepository {
    private Repository repository;

    /**
     * The public constructor.
     * @param repository - {@link ItemRepository}
     */
    @Inject
    public ItemRepositoryImpl(final Repository repository){
        this.repository = repository;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items;
        items = this.repository.findAll();
        return items != null ? items : new ArrayList<>();
    }

    @Override
    public Optional<Item> findById(final Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void deleteById(final Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public Item save(final Item item){
        this.repository.save(item);
        return item;
    }
}
