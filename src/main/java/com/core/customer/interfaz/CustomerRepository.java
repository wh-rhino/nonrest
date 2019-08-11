package com.core.customer.interfaz;

import java.util.List;
import com.core.customer.document.Customer;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {
    public Customer findByFirstName(String firstName);
    public List<Customer> findBySecondName(String secondName);
}