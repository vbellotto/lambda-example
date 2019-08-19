package com.vitor.example.domain.exception;

import java.net.HttpURLConnection;

/**
 * Item not found exception.
 *
 */
public class ItemNotFoundException extends Exception {

    private static final String ITEM_NOT_FOUND = "Item with the id %s could not be found.";

    private final int errorCode;
    private final String message;

    /**
     * The default constructor.
     * @param id - the item id
     */
    public ItemNotFoundException(final Long id){
        this.errorCode = HttpURLConnection.HTTP_NOT_FOUND;
        this.message = String.format(ITEM_NOT_FOUND, String.valueOf(id));
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
