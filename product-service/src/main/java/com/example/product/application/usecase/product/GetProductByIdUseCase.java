package com.example.product.application.usecase.product;

import com.example.product.dto.model.ProductDto;

public interface GetProductByIdUseCase {
    ProductDto execute(short id);
}