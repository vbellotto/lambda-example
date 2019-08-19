package com.vitor.example.domain.validator;

import com.vitor.example.domain.constants.RequestParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates request path parameters.
 *
 */
public class ParameterValidator {
    private Map<String, String> pathParameters;
    private List<String> errors;
    /**
     * The default constructor.
     *
     * @param pathParameters - the path parameters to validate
     */
    public ParameterValidator(final Map<String, String> pathParameters) {
        this.errors = new ArrayList<>();
        this.pathParameters = pathParameters;
    }

    /**
     * Validate request parameters.
     */
    public void validate(){
        validateVssAttributeId();
    }

    /**
     * Validity indicator method.
     * @return - Validity indicator
     */
    public boolean isValid(){
        return this.errors.isEmpty();
    }

    /**
     * Get the validation error list.
     *
     * @return - the validation error list.
     */
    public List<String> getErrors() {
        return errors;
    }

    private void validateVssAttributeId() {
        String itemIdString = this.pathParameters.get(RequestParameters.ITEM_ID);

        if (itemIdString == null || itemIdString.isEmpty()) {
            this.errors.add(String.format(RequestError.ITEM_ID_MISSING.getMessage(),
                    RequestParameters.ITEM_ID));
        } else {
            try{
                Long.parseLong(itemIdString);
            } catch (final NumberFormatException numberFormatException) {
                this.errors.add(String.format(RequestError.ITEM_ID_INVALID_FORMAT.getMessage(),
                        itemIdString, RequestParameters.ITEM_ID));
            }
        }
    }
}
