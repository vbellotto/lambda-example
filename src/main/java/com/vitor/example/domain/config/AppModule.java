package com.vitor.example.domain.config;

import com.vitor.example.domain.repository.ItemRepository;
import com.vitor.example.domain.repository.impl.ItemRepositoryImpl;
import com.vitor.example.domain.service.ItemService;
import com.vitor.example.domain.service.impl.ItemServiceImpl;
import com.vitor.example.infrastructure.DynamoDBClient;
import com.vitor.example.infrastructure.Repository;
import com.vitor.example.infrastructure.dynamodb.DynamoDBClientImpl;
import com.vitor.example.infrastructure.dynamodb.DynamoDBRepositoryImpl;
import dagger.Module;
import dagger.Provides;

/**
 * The Dagger 2 Module.
 *
 * Specifies all the providers to be injected.
 *
 */
@Module
public class AppModule {

    /**
     * Provide the service implementation.
     *
     * @param itemService - The service implementation
     * @return - The service implementation instance
     */
    @Provides
    ItemService provideService(final ItemServiceImpl itemService) {
        return itemService;
    }

    /**
     * Provide the item repository implementation.
     *
     * @param itemRepository {@link ItemRepositoryImpl}
     * @return - {@link ItemRepository}
     */
    @Provides
    ItemRepository provideCampaignRepository(final ItemRepositoryImpl itemRepository){
        return itemRepository;
    }

    /**
     * Provide the DynamoDB repository implementation.
     *
     * @param repository {@link DynamoDBRepositoryImpl}
     * @return {@link DynamoDBRepositoryImpl}
     */
    @Provides
    Repository provideRepository(final DynamoDBRepositoryImpl repository){
        return repository;
    }

    /**
     * Provide the DynamoDB client implementation.
     *
     * @param dynamoDBClient {@link DynamoDBClientImpl}
     * @return {@link DynamoDBClientImpl}
     */
    @Provides
    DynamoDBClient provideDynamoDBClient(final DynamoDBClientImpl dynamoDBClient){
        return dynamoDBClient;
    }
}
