package com.vitor.example.domain.service.impl;

import com.vitor.example.domain.assembler.ItemAssembler;
import com.vitor.example.domain.assembler.ItemSummaryAssembler;
import com.vitor.example.domain.entity.Item;
import com.vitor.example.domain.exception.ItemNotFoundException;
import com.vitor.example.domain.model.ItemSummary;
import com.vitor.example.domain.repository.ItemRepository;
import com.vitor.example.domain.service.ItemService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * The {@link ItemService} implementation.
 *
 */
@Singleton
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;

    /**
     * The default constructor.
     * @param itemRepository - the ItemRepository
     */
    @Inject
    public ItemServiceImpl(final ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemSummary> findAll() {
        List<ItemSummary> itemSummaries;
        itemSummaries = ItemSummaryAssembler.fromItemList(this.itemRepository.findAll());
        return itemSummaries;
    }

    @Override
    public ItemSummary findById(final Long id) throws ItemNotFoundException {
        Optional<Item> item = this.itemRepository.findById(id);

        if (item.isPresent()){
            return ItemSummaryAssembler.fromItem(item.get());
        } else {
            throw new ItemNotFoundException(id);
        }
    }

    @Override
    public void deleteById(final Long id) {
        this.itemRepository.deleteById(id);
    }

    @Override
    public ItemSummary insert(final ItemSummary itemSummary){
        Item itemToInsert = ItemAssembler.fromItemSummary(itemSummary);
        return ItemSummaryAssembler.fromItem(this.itemRepository.save(itemToInsert));
    }
}
