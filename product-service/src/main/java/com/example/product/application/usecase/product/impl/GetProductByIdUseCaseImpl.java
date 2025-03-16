package com.example.product.application.usecase.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.product.application.exceptions.NotFoundException;
import com.example.product.application.usecase.product.GetProductByIdUseCase;
import com.example.product.dto.mapper.ProductMapper;
import com.example.product.dto.model.ProductDto;
import com.example.product.infra.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {
    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(GetByCategoryUseCaseImpl.class);

    @Override
    public ProductDto execute(short id) {
        return this.productRepository.findById(id)
                .map(product -> {
                    ProductDto productDto = this.productMapper.mapToDto(product);
                    productDto.setVendor(product.getVendorEntity().getName());
                    productDto.setCategory(product.getCategoryEntity().getName());
                    return productDto;
                })
                .orElseThrow(() -> {
                    logger.warn("Product with id {} not found", id);
                    return new NotFoundException("Product not found, please try again later.");
                });
    }
}
