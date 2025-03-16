package com.example.cart_service.application.usecase.cart.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.cart_service.application.usecase.cart.DeleteCartUseCase;
import com.example.cart_service.services.CartRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteCartUseCaseImpl implements DeleteCartUseCase {

    private final CartRedisService cartRedisService;
    private final Logger logger = LoggerFactory.getLogger(DeleteCartUseCaseImpl.class);

    @Override
    public void execute(String userId, List<Integer> listProductId) {
        try {
            this.cartRedisService.deleteProductInCart(userId, listProductId);
        } catch (Exception e) {
            logger.error("Error occurred while deleting cart: {}" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
