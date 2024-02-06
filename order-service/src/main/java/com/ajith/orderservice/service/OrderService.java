package com.ajith.orderservice.service;

import com.ajith.orderservice.dto.OrderLineItemsDto;
import com.ajith.orderservice.dto.OrderRequest;
import com.ajith.orderservice.model.Order;
import com.ajith.orderservice.model.OrderLineItems;
import com.ajith.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest)
    {
        Order order = new Order ();
        order.setOrderNumber ( UUID.randomUUID ().toString () );
        List< OrderLineItems > orderLineItems = orderRequest.getOrderLineItemsDtoList ()
                 .stream ()
                 .map ( this::mapToOrderLineItemsDto ).toList ();
        order.setOrderLineItems ( orderLineItems );
        orderRepository.save ( order );
    }

    private OrderLineItems mapToOrderLineItemsDto (OrderLineItemsDto orderLineItem) {
     return  OrderLineItems.builder ( )
                .price ( orderLineItem.getPrice () )
                .skuCode ( orderLineItem.getSkuCode () )
                .quantity ( orderLineItem.getQuantity ( ) )
                .build ( );

    }
}