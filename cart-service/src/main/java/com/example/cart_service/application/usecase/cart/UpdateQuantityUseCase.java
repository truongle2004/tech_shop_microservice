package com.example.cart_service.application.usecase.cart;

public interface UpdateQuantityUseCase {
    void execute(String userId, int quantity, int productId);
}
