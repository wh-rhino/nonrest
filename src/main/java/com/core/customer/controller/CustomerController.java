package com.core.customer.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import com.core.customer.document.Customer;
import com.core.customer.exception.CustomerNotFoundException;
import com.core.customer.interfaz.CustomerRepository;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;

@RestController
class CustomerController{

    @Autowired(required = true)
    CustomerRepository repository;
    @Autowired(required = true)
    CustomerResourceAssambler assambler;

    /**
     * curl http://localhost:8080/customers/5d4ef92b66dca14d45fdffbd
     */
    @GetMapping("/customers/{id}")
    Resource<Customer> getCustomerById(@PathVariable String id) {
        Customer customer = repository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));
        return assambler.toResource(customer);
    }

    /**
     * curl http://localhost:8080/customers
     */
    @GetMapping("/customers")
    Resources<Resource<Customer>> getCustomer() {
        List<Resource<Customer>> customers = repository.findAll().stream()
            .map(assambler::toResource)
            .collect(Collectors.toList());
        return new Resources<>(customers,
            linkTo(methodOn(CustomerController.class).getCustomer()).withSelfRel());
    }

     /**
     * curl -X POST localhost:8080/customers -H 'Content-type:application/json' -d '{"firstName": "Roberto", "secondName": "Carlos"}'
     * 
     * @throws URISyntaxException
     */
    @PostMapping("/customers")
    ResponseEntity<?> createCustomer(@RequestBody Customer newCustomer) throws URISyntaxException{
        Resource<Customer> resource = assambler.toResource(repository.save(newCustomer));
        return ResponseEntity
            .created(new URI(resource.getId().expand().getHref()))
            .body(resource);
    }

    /**
     * curl -X PUT localhost:8080/customers/5d4f5e6f36f6a3049689e2fa -H 'Content-type:application/json' -d '{"firstName": "MR", "secondName": "ROBOT"}'
     * 
     * @param newCustomer
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/customers/{id}")
    ResponseEntity<?> updateCustomerById(@RequestBody Customer newCustomer, @PathVariable String id)
            throws URISyntaxException {
       Customer updateCustomer = repository.findById(id)
        .map(customer -> {
            customer.setFirstName(newCustomer.getFirstName());
            customer.setSecondName(newCustomer.getSecondName());
            return repository.save(customer); 
        })
        .orElseGet(() ->{
            newCustomer.setId(id);
            return repository.save(newCustomer);
        });

        Resource<Customer> resource = assambler.toResource(updateCustomer);
        
        return ResponseEntity
            .created(new URI(resource.getId().expand().getHref()))
            .body(resource);
    }
    
    /**
     * curl -X DELETE localhost:8080/customers/5d4efd19f5e5480b32bd1339
     */
    @DeleteMapping("/customers/{id}")
    ResponseEntity<?> deleteCustomerById(@PathVariable String id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}