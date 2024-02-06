package com.ajith.orderservice.controller;

import com.ajith.orderservice.dto.OrderRequest;
import com.ajith.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private  final OrderService orderService;
    @PostMapping
    @ResponseStatus( HttpStatus.OK )
    public String placeOrder(@RequestBody OrderRequest orderRequest)
    {
        orderService.placeOrder ( orderRequest );
        return "Order created successfully";
    }
}
