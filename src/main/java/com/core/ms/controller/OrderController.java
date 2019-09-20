package com.core.ms.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.core.ms.document.Order;
import com.core.ms.exception.OrderNotFoundException;
import com.core.ms.interfaz.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
class OrderController {

    @Autowired(required = true)
    OrderRepository repository;
    @Autowired
    OrderResourceAssambler assembler;

    OrderController() {
    }
    
    @GetMapping("/orders")
    Resources <Resource<Order>> getAllOrder(){
        List<Resource<Order>> orders = repository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());
		return new Resources<>(orders,
			linkTo(methodOn(OrderController.class).getAllOrder()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    Resource <Order> getOrder(@PathVariable String id){
        return assembler.toResource(repository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @PostMapping("/orders")
    ResponseEntity<Resource<Order>> createOrder(@RequestBody Order order){
        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = repository.save(order);
        return ResponseEntity
            .created(linkTo(methodOn(OrderController.class).getOrder(newOrder.getId().toString())).toUri())
            .body(assembler.toResource(newOrder));
    }

    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<ResourceSupport> cancel(@PathVariable String id){
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toResource(repository.save(order)));
        }
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    @PutMapping("/orders/{id}/complete")
    ResponseEntity<ResourceSupport> complete(@PathVariable String id){
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        
        if(order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toResource(repository.save(order)));
        }

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new VndErrors.VndError("Method not allowed", "You can't complete an order that is in the " + order.getStatus() + " status"));
    }

}