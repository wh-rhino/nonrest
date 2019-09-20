package com.core.ms.interfaz;

import com.core.ms.document.Order;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}