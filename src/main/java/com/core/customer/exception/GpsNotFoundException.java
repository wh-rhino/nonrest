package com.core.customer.exception;

import org.bson.types.ObjectId;

public class GpsNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GpsNotFoundException(ObjectId id) {
        super("Could not find GPS " + id);
    }
}