package com.example.cart_service.application.usecase.cart;

import java.util.List;

public interface DeleteCartUseCase {
    void execute(String userId, List<Integer> listProductId);
}
