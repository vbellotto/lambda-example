package com.vitor.example.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * The Item entity.
 *
 * @author - Vitor.Bellotto@criticaltechworks.com on 19/08/2019
 */
@DynamoDBTable(tableName="item")
public class Item{

    private Long id;
    private String name;
    private String description;
    private String creator;
    private String version;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreator() {
        return creator;
    }

    public String getVersion() {
        return version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}