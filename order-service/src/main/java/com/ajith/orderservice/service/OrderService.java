package com.ajith.orderservice.service;

import com.ajith.orderservice.dto.InventoryResponse;
import com.ajith.orderservice.dto.OrderLineItemsDto;
import com.ajith.orderservice.dto.OrderRequest;
import com.ajith.orderservice.model.Order;
import com.ajith.orderservice.model.OrderLineItems;
import com.ajith.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest)
    {
        Order order = new Order ();
        order.setOrderNumber ( UUID.randomUUID ().toString () );
        List< OrderLineItems > orderLineItems = orderRequest.getOrderLineItemsDtoList ()
                 .stream ()
                 .map ( this::mapToOrderLineItemsDto ).toList ();
        order.setOrderLineItems ( orderLineItems );

        List<String> skuCodes =  order.getOrderLineItems ().stream ()
                .map ( OrderLineItems::getSkuCode ).toList ();


        //Todo:call inventory and check stock
         InventoryResponse[] inventoryResponsesArray = webClientBuilder.build ()
                        .get ()
                        .uri ( "http://inventory-service/api/inventory"
                        ,uriBuilder -> uriBuilder.queryParam ("skuCode" ,skuCodes ).build (  ))
                        .retrieve ()
                        .bodyToMono ( InventoryResponse[].class )
                        .block ();

        boolean allProductsInStock = Arrays.stream ( inventoryResponsesArray )
                .allMatch ( InventoryResponse::isInStock );


        if(allProductsInStock)
        {
            orderRepository.save ( order );
        }
        else {
            throw new IllegalArgumentException ( "Product is not in stock" );
        }
    }

    private OrderLineItems mapToOrderLineItemsDto (OrderLineItemsDto orderLineItem) {
     return  OrderLineItems.builder ( )
                .price ( orderLineItem.getPrice () )
                .skuCode ( orderLineItem.getSkuCode () )
                .quantity ( orderLineItem.getQuantity ( ) )
                .build ( );

    }
}