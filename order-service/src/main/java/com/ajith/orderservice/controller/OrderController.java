package com.ajith.orderservice.controller;

import com.ajith.orderservice.service.OrderService;
import com.ajith.orderservice.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private  final OrderService orderService;
    @PostMapping
    @ResponseStatus( HttpStatus.OK )
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest)
    {
         return  orderService.placeOrder ( orderRequest );
    }


}
