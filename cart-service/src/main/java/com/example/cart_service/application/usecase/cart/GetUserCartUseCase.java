package com.example.cart_service.application.usecase.cart;

import java.util.List;

import com.example.cart_service.dto.model.ProductDto;

public interface GetUserCartUseCase {
    List<ProductDto> execute(String userId);
}
