package com.vitor.example.infrastructure;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

/**
 * Gets the DynamoDb client.
 */
@FunctionalInterface
public interface DynamoDBClient {
    /**
     * Gets the DynamoDb client instance.
     *
     * @return - The DynamoDb client instance
     */
    AmazonDynamoDB getDynamoDbClient();
}
