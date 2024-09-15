package com.integration.entity;

public class Program {

    private String id;
    private String name;
    private String description;

    // Add any other DHIS2 program-specific fields here
    // Example fields like startDate, endDate, etc.

    public Program() {
    }

    public Program(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
