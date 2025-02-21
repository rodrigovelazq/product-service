package com.rodrigovelazq.product_service.mapper;

import com.rodrigovelazq.product_service.domain.Product;
import com.rodrigovelazq.product_service.domain.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto mapTo(Product product);
    Product mapFrom(ProductDto productDTO);
}