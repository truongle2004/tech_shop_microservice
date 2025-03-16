package com.example.cart_service.application.usecase.cart.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.cart_service.application.usecase.cart.UpdateQuantityUseCase;
import com.example.cart_service.services.CartRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateQuantityUseCaseImpl implements UpdateQuantityUseCase {
    private final CartRedisService cartRedisService;
    private final Logger logger = LoggerFactory.getLogger(UpdateQuantityUseCaseImpl.class);

    @Override
    public void execute(String userId, int quantity, int productId) {
        try {
            cartRedisService.updateProductInCart(userId, quantity, productId);
        } catch (Exception e) {
            logger.error("Error occurred while updating product quantity in cart: {}", e);
            throw new RuntimeException(e);
        }
    }

}
