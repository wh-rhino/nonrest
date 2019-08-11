package com.core.customer.document;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {    
    @Id
    private ObjectId id;
    private String firstName;
    private String secondName;

    public Customer() {
    }

    public Customer(String firstName, String secondName, ObjectId id) {
            this.id = id;
            this.firstName = firstName;
            this.secondName = secondName;
    }

    public String toString(){
        return String.format("Customer[id=%s, firstName='%s', lastName='%s']", id, firstName, secondName);
    }

    public String getId() {
        return id.toHexString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }
}   