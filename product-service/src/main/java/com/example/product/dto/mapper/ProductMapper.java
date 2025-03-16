package com.example.product.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.product.dto.model.ProductDto;
import com.example.product.entities.ProductEntity;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductMapper {
    private ModelMapper modelMapper;

    public ProductDto mapToDto(ProductEntity product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
