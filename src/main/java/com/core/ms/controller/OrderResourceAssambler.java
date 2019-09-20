package com.core.ms.controller;

import com.core.ms.document.Order;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderResourceAssambler implements ResourceAssembler <Order, Resource <Order>> {

    @Override
    public Resource<Order> toResource(Order order) {

        Resource<Order> orderResource = new Resource<Order>(order, 
            linkTo(methodOn(OrderController.class).getOrder(order.getId().toString())).withSelfRel(),
            linkTo(methodOn(OrderController.class).getAllOrder()).withRel("orders")
        );

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderResource.add(
                linkTo(methodOn(OrderController.class)
                    .cancel(order.getId())).withRel("cancel"));
            orderResource.add(
                linkTo(methodOn(OrderController.class)
                    .complete(order.getId())).withRel("complete"));
        }

        return orderResource;
    }

}