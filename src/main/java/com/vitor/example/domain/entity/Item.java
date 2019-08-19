package com.vitor.example.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * The Item entity.
 *
 */
@DynamoDBTable(tableName="item")
public class Item{

    private Long id;
    private String name;
    private String description;
    private String creator;
    private String status;
    private String version;
    private String type;

    /**
     * The default constructor.
     */
    public Item(){}

    @DynamoDBRangeKey(attributeName="id")
    @DynamoDBIndexRangeKey(attributeName="id",
            globalSecondaryIndexName = "statusIndex")
    public Long getId() {
        return id;
    }

    @DynamoDBIndexHashKey(attributeName="status",
            globalSecondaryIndexName = "statusIndex")
    public String getStatus() {
        return status;
    }

    @DynamoDBAttribute(attributeName="name")
    public String getName() {
        return name;
    }

    @DynamoDBAttribute(attributeName="description")
    public String getDescription() {
        return description;
    }

    @DynamoDBAttribute(attributeName="creator")
    public String getCreator() {
        return creator;
    }

    @DynamoDBAttribute(attributeName="version")
    public String getVersion() {
        return version;
    }

    @DynamoDBHashKey(attributeName="type")
    public String getType() {
        return type;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setCreator(final String creator) {
        this.creator = creator;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
