package com.example.cart_service.application.usecase.cart;

import com.example.cart_service.dto.request.CartItemRequest;

public interface AddCartItemUseCase {
    void execute(CartItemRequest cartItemRequest);
}
