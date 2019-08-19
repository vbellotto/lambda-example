package com.vitor.example.infrastructure.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.vitor.example.infrastructure.DynamoDBClient;
import com.vitor.example.infrastructure.environment.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The DynamoDb client Wrapper.
 *
 */
@Singleton
public class DynamoDBClientImpl implements DynamoDBClient {

    private final Logger log = LogManager.getLogger(DynamoDBClientImpl.class);
    private final AmazonDynamoDB dynamoDBClient;

    /**
     * The public constructor.
     */
    @Inject
    public DynamoDBClientImpl() {
        this.dynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Environment.getRegion()).build();
        log.info("The dynamoDb client for the zone {}, was created with : {} id",
                Environment.getRegion(), dynamoDBClient.toString());
    }

    /**
     * Getter for the DynamoDb client.
     *
     * @return - The DynamoDb client instance
     */
    @Override
    public AmazonDynamoDB getDynamoDbClient() {
        return this.dynamoDBClient;
    }
}
