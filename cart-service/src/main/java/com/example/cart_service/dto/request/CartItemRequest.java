package com.example.cart_service.dto.request;

import lombok.Getter;

@Getter
public class CartItemRequest {
    private String userId;
    private short quantity;
    private int productId;
}
