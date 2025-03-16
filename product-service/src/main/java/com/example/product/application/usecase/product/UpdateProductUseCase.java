package com.example.product.application.usecase.product;

import com.example.product.dto.request.UpdateProductRequest;

public interface UpdateProductUseCase {
    void execute(short id, UpdateProductRequest updateProductRequest);
}
