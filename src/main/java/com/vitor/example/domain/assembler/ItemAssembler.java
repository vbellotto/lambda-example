package com.vitor.example.domain.assembler;

/**
 * Create an {@link Item} object.
 *
 * @author - Vitor.Bellotto@criticaltechworks.com on 19/08/2019
 */
public class ItemAssembler {

    /**
     * The default constructor.
     */
    public ItemAssembler() {
    }

    public static Item fromItemSummary(final ItemSummary itemSummary) {
        Item item = new Item();

        item.setCreator(itemSummary.getCreator());
        item.setDescription(itemSummary.getDescription());
        item.setName(itemSummary.getName());
        item.setVersion(itemSummary.getVersion());

        return item;
    }
}
