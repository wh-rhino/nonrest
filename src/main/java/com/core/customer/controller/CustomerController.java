package com.core.customer.controller;

import java.util.List;

import com.core.customer.document.Customer;
import com.core.customer.interfaz.CustomerRepository;
import com.core.customer.exception.CustomerNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CustomerController{

    @Autowired(required=true)
    private CustomerRepository repository;

    /**
     * curl localhost:8080/customers/5d4ef92b66dca14d45fdffbd
     * @param id
     * @return
     */
    @GetMapping("/customers/{id}")
    Customer getCustomerById(@PathVariable ObjectId id){
        return repository.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException(id));
    }

    /**
     * curl -X POST localhost:8080/customers -H 'Content-type:application/json' -d '{"firstName": "Roberto", "secondName": "Carlos"}'
     */
    @PostMapping("/customers")
    Customer createCustomer(@RequestBody Customer newCustomer){
        return repository.save(newCustomer);
    }

    /**
     * curl localhost:8080/customers/   
     */
    @GetMapping("/customers")
    List<Customer> getCustomer(){
        return repository.findAll();
    }

    /**
     * curl -X PUT localhost:8080/customers/5d4f5e6f36f6a3049689e2fa -H 'Content-type:application/json' -d '{"firstName": "MR", "secondName": "ROBOT"}'
     * @param newCustomer
     * @param id
     * @return
     */
    @PutMapping("/customers/{id}")
    Customer updateCustomerById(@RequestBody Customer newCustomer, @PathVariable ObjectId id){
        return repository.findById(id)
            .map(customer -> {
                customer.setFirstName(newCustomer.getFirstName());  
                customer.setSecondName(newCustomer.getSecondName());
                return repository.save(customer);
            })
            .orElseGet(() -> {
                newCustomer.setId(id);
                return repository.save(newCustomer);
            });
    }

    /**
     * curl -X DELETE localhost:8080/customers/5d4efd19f5e5480b32bd1339
     */
    @DeleteMapping("/customers/{id}")
    void deleteCustomerById(@PathVariable ObjectId id){
        repository.deleteById(id);
    }
}