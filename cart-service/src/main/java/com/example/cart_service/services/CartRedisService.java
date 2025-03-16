package com.example.cart_service.services;

import java.util.List;

import com.example.cart_service.dto.request.CartItemRequest;

public interface CartRedisService {
    void addProductToCart(CartItemRequest cartItemRequest);

    void updateProductInCart(String userId, short quantity, int productId);

    List<String> getProductsFromCart(String userId);
}
