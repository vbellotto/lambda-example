package com.vitor.example.domain.model;

/**
 * The ItemSummary model.
 *
 */
public final class ItemSummary {

    private Long id;
    private String name;
    private String description;
    private String creator;
    private String version;

    /**
     * The public constructor.
     */
    public ItemSummary(){}

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(final String creator) {
        this.creator = creator;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }
}
