package com.rodrigovelazq.product_service.controller;

import com.rodrigovelazq.product_service.domain.Product;
import com.rodrigovelazq.product_service.domain.dto.ProductContactInfoDto;
import com.rodrigovelazq.product_service.domain.dto.ProductDto;
import com.rodrigovelazq.product_service.mapper.ProductMapper;
import com.rodrigovelazq.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    @Autowired
    private ProductContactInfoDto productContactInfoDto;
    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAllProduct(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return new ResponseEntity<>(products.map(productMapper::mapTo), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.mapFrom(productDto);
        Product saved = productService.save(product);
        return new ResponseEntity<>(productMapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto>  getProduct(@PathVariable("id") Long id) {
        Optional<Product> foundProduct = productService.findById(id);
        return foundProduct.map(product -> {
            ProductDto productDto = productMapper.mapTo(product);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDto> fullUpdateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDto productDto) {
        if(productService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDto.setId(id);
        Product product = productMapper.mapFrom(productDto);
        Product saved = productService.save(product);

        return new ResponseEntity<>(productMapper.mapTo(saved), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProductDto> partialUpdateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDto productDto) {
        if(productService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productMapper.mapFrom(productDto);
        Product saved = productService.partialUpdate(id, product);

        return new ResponseEntity<>(productMapper.mapTo(saved), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/contact-info")
    public ResponseEntity<ProductContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productContactInfoDto);
    }
}
