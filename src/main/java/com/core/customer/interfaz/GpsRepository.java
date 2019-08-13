package com.core.customer.interfaz;

import com.core.customer.document.Gps;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GpsRepository extends MongoRepository<Gps, ObjectId> {
}