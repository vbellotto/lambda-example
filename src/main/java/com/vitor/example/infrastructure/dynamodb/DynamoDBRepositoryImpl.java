package com.vitor.example.infrastructure.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.vitor.example.domain.entity.Item;
import com.vitor.example.infrastructure.DynamoDBClient;
import com.vitor.example.infrastructure.Repository;
import com.vitor.example.infrastructure.environment.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for {@link Repository}.
 **/
@Singleton
public class DynamoDBRepositoryImpl implements Repository {
    private final Logger logger = LogManager.getLogger(DynamoDBRepositoryImpl.class);

    private final AmazonDynamoDB amazonDynamoDB;
    private final DynamoDBMapper mapper;
    private final DynamoDBMapperConfig mapperConfiguration;

    /**
     * The public constructor.
     *
     * @param dynamoDbClient The dynamoDB client
     */
    @Inject
    public DynamoDBRepositoryImpl(final DynamoDBClient dynamoDbClient){
        this.amazonDynamoDB = dynamoDbClient.getDynamoDbClient();
        this.mapperConfiguration = DynamoDBMapperConfig.builder()
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(Environment.getTableName()))
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .build();
        this.mapper = new DynamoDBMapper(dynamoDbClient.getDynamoDbClient(), mapperConfiguration);
    }


    @Override
    public List<Item> findAll() {
        logger.info("Find all request");
        DynamoDBScanExpression query = new DynamoDBScanExpression();
        PaginatedScanList<Item> items = this.mapper.scan(Item.class, query);

        List<Item> itemsList = this.mapper.scan(Item.class, query);

        return itemsList;
    }

    @Override
    public Optional<Item> findById(final Long id) {
        logger.info("FindById request");
        return Optional.ofNullable(this.mapper.load(Item.class, "type1", id));
    }

    @Override
    public void deleteById(final Long id) {
        logger.info("Delete request");
        Item itemToDelete = mapper.load(Item.class, id, mapperConfiguration);
        this.mapper.delete(itemToDelete);

    }

    @Override
    public void save(final Item item) {
        logger.info("Save request");
        this.mapper.save(item, mapperConfiguration);
    }
}
