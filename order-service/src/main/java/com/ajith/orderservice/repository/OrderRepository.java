package com.ajith.orderservice.repository;

import com.ajith.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository< Order,Long > {
}
