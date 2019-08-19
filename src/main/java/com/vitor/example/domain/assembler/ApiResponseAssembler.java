package com.vitor.example.domain.assembler;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.vitor.example.domain.model.ItemSummary;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates new API gateway proxy response object.
 *
 */
public final class ApiResponseAssembler {
    private ApiResponseAssembler() {
        //Avoid instantiation
    }

    /**
     * Creates a new instance with HTTP 200.
     * @param itemSummary the {@link ItemSummary}
     * @return a new instance of {@link APIGatewayProxyResponseEvent}
     */
    public static APIGatewayProxyResponseEvent ok(final ItemSummary itemSummary){
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        response.setHeaders(getDefaultHeaders());
        response.setStatusCode(HttpURLConnection.HTTP_OK);
        response.setBody(new JSONObject(itemSummary).toString());

        return response;
    }

    /**
     * Creates a new instance with HTTP 200 from a list of items.
     * @param items list of campaigns
     * @return a new instance of {@link APIGatewayProxyResponseEvent}
     */
    public static APIGatewayProxyResponseEvent ok(final List<ItemSummary> items){
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        response.setHeaders(getDefaultHeaders());
        response.setStatusCode(HttpURLConnection.HTTP_OK);
        response.setBody(new JSONArray(items).toString());

        return response;
    }

    /**
     * Creates a new instance with HTTP 400 from a list of errors.
     *
     * @param errors the list of errors
     * @param statusCode the status code
     * @return a new instance of {@link APIGatewayProxyResponseEvent}
     */
    public static APIGatewayProxyResponseEvent error(final int statusCode, final List<String> errors) {
        return new APIGatewayProxyResponseEvent()
                .withHeaders(getDefaultHeaders())
                .withStatusCode(statusCode)
                .withBody(new JSONArray(errors).toString());
    }

    private static Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.toString());
        headers.put(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.GET.toString());
        headers.put(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "'Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token'");
        headers.put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return headers;
    }
}
