package com.vitor.example.domain.validator;

/**
 * Request Error enum.
 *
 */
public enum RequestError {

    /**
     * Missing item id error.
     */
    ITEM_ID_MISSING("Missing path parameter: %s"),

    /**
     * Invalid item id format.
     */
    ITEM_ID_INVALID_FORMAT("Invalid format item id format: %s.");

    private String message;

    RequestError(final String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
