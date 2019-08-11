package com.core.customer.exception;

import org.bson.types.ObjectId;

public class CustomerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(ObjectId id) {
        super("Could not find employee " + id);
    }
}