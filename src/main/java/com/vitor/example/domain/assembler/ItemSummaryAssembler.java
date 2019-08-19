package com.vitor.example.domain.assembler;

import com.vitor.example.domain.entity.Item;
import com.vitor.example.domain.model.ItemSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates an {@link ItemSummary} object.
 *
 */
public class ItemSummaryAssembler {

    /**
     * The default constructor.
     */
    public ItemSummaryAssembler(){}

    /**
     * Creates a ItemSummary object from an Item.
     * @param item - the Item
     * @return - the ItemSummary
     */
    public static ItemSummary fromItem(final Item item){
        ItemSummary itemSummary = new ItemSummary();

        itemSummary.setId(item.getId());
        itemSummary.setCreator(item.getCreator());
        itemSummary.setDescription(item.getDescription());
        itemSummary.setName(item.getName());
        itemSummary.setVersion(item.getVersion());

        return itemSummary;
    }

    /**
     * Creates a List of ItemSummary from a List of Items.
     * @param items - the Item list
     * @return - the ItemSummary list
     */
    public static List<ItemSummary> fromItemList(final List<Item> items){
        List<ItemSummary> itemSummaries = new ArrayList<>();
        items.forEach((item) -> itemSummaries.add(ItemSummaryAssembler.fromItem(item)));
        return itemSummaries;
    }
}
