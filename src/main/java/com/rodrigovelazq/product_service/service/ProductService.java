package com.rodrigovelazq.product_service.service;

import com.rodrigovelazq.product_service.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Product save(Product product);

    Optional<Product> getProductByTitle(String name);

    boolean isExists(Long id);

    Optional<Product> findById(Long id);

    Product partialUpdate(Long id, Product product);

    void delete(Long id);
}
