package com.example.cart_service.application.usecase.cart.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.cart_service.application.usecase.cart.DeleteAllCartUseCase;
import com.example.cart_service.services.CartRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteAllCartUseCaseImpl implements DeleteAllCartUseCase {

    private final CartRedisService cartRedisService;
    private final Logger logger = LoggerFactory.getLogger(DeleteAllCartUseCaseImpl.class);

    @Override
    public void execute(String userId) {
        try {
            this.cartRedisService.deleteAllCart(userId);
        } catch (Exception e) {
            logger.error("Error occurred while deleting all cart: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
