package com.core.ms.document;

import com.core.ms.controller.Status;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {

    private String id;
    private String description;
    private Status status;

    public Order() {
    }

    public Order(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order [description=" + description + ", id=" + id + ", status=" + status + "]";
    }
    
    
}