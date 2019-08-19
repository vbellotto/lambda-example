package com.vitor.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.vitor.example.domain.assembler.ApiResponseAssembler;
import com.vitor.example.domain.config.AppComponent;
import com.vitor.example.domain.config.DaggerAppComponent;
import com.vitor.example.domain.constants.RequestParameters;
import com.vitor.example.domain.exception.ItemNotFoundException;
import com.vitor.example.domain.service.ItemService;
import com.vitor.example.domain.validator.ParameterValidator;
import com.vitor.example.domain.validator.RequestError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handle the Get item request.
 *
 */
public class GetItemsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final Logger logger = LogManager.getLogger(GetItemsHandler.class);
    private final ItemService itemService;

    /**
     * Default constructor, used by lambda initializer.
     */
    public GetItemsHandler(){
        AppComponent appComponent = DaggerAppComponent.builder().build();
        this.itemService = appComponent.getItemService();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent apiRequestEvent, final Context context) {
        logger.info("Get item request handler.");

        APIGatewayProxyResponseEvent response;

        Map<String, String> pathParameters = apiRequestEvent.getPathParameters();

        if (pathParameters != null && !pathParameters.isEmpty()) {
            ParameterValidator validator = new ParameterValidator(pathParameters);
            validator.validate();

            if (validator.isValid()) {
                String itemIdString = apiRequestEvent.getPathParameters().get(RequestParameters.ITEM_ID);
                Long itemId = Long.parseLong(itemIdString);
                try {
                    response = ApiResponseAssembler.ok(itemService.findById(itemId));
                } catch (final ItemNotFoundException exception) {
                    List<String> errors = new ArrayList<>();
                    errors.add(String.format(RequestError.ITEM_ID_INVALID_FORMAT.getMessage(), itemId));
                    response = ApiResponseAssembler.error(exception.getErrorCode(), errors);
                }
            } else {
                response = ApiResponseAssembler
                        .error(HttpURLConnection.HTTP_BAD_REQUEST, validator.getErrors());
            }

        } else {
            response = ApiResponseAssembler.ok(this.itemService.findAll());
        }

        return response;
    }
}
