package com.core.ms.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.core.ms.document.Customer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class CustomerResourceAssambler implements ResourceAssembler<Customer, Resource<Customer>>{
    
    @Override
    public Resource<Customer> toResource(Customer customer){
        return new Resource<>(customer,
            linkTo(methodOn(CustomerController.class).getCustomerById(customer.getId())).withSelfRel(),
            linkTo(methodOn(CustomerController.class).getCustomer()).withRel("customers"));
    }
}