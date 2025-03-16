package com.example.cart_service.services;

import java.util.List;

import com.example.cart_service.dto.model.ProductDto;
import com.example.cart_service.dto.request.CartItemRequest;

public interface CartRedisService {
    void addProductToCart(CartItemRequest cartItemRequest);

    void updateProductInCart(String userId, int quantity, int productId);

    void deleteAllCart(String userId);

    void deleteProductInCart(String userId, List<Integer> listProductId);

    List<ProductDto> getProductsFromCart(String userId);
}
