package com.rodrigovelazq.product_service.service.impl;

import com.rodrigovelazq.product_service.domain.Product;
import com.rodrigovelazq.product_service.exception.ResourceNotFoundException;
import com.rodrigovelazq.product_service.repository.ProductRepository;
import com.rodrigovelazq.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductByTitle(String name) {
        return this.productRepository.findByTitle(name);
    }

    @Override
    public boolean isExists(Long id) {
        return this.productRepository.existsById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Product partialUpdate(Long id, Product product) {
        product.setId(id);

        return this.productRepository.findById(id).map(existingProduct -> {
            Optional.ofNullable(product.getTitle()).ifPresent(existingProduct::setTitle);
            Optional.ofNullable(product.getDescription()).ifPresent(existingProduct::setDescription);
            Optional.ofNullable(product.getPrice()).ifPresent(existingProduct::setPrice);
            Optional.ofNullable(product.getCategory()).ifPresent(existingProduct::setCategory);
            Optional.ofNullable(product.getImage()).ifPresent(existingProduct::setImage);
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
    }

    @Override
    public void delete(Long id) {
        this.productRepository.deleteById(id);
    }
}
