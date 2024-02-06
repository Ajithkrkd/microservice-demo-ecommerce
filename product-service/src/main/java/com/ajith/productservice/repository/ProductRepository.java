package com.ajith.productservice.repository;

import com.ajith.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository  extends MongoRepository< Product ,String > {
}
