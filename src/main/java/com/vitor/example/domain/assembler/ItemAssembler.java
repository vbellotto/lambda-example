package com.vitor.example.domain.assembler;

import com.vitor.example.domain.entity.Item;
import com.vitor.example.domain.model.ItemSummary;

/**
 * Create an {@link Item} object.
 *
 */
public class ItemAssembler {

    /**
     * The default constructor.
     */
    public ItemAssembler() {
    }

    /**
     * Creantes a new Item from a ItemSummary.
     * @param itemSummary - the ItemSummary
     * @return - the Item
     */
    public static Item fromItemSummary(final ItemSummary itemSummary) {
        Item item = new Item();

        item.setCreator(itemSummary.getCreator());
        item.setDescription(itemSummary.getDescription());
        item.setName(itemSummary.getName());
        item.setVersion(itemSummary.getVersion());

        return item;
    }
}
