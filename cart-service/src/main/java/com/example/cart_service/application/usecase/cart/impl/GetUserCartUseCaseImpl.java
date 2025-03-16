package com.example.cart_service.application.usecase.cart.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.cart_service.application.usecase.cart.GetUserCartUseCase;
import com.example.cart_service.dto.model.ProductDto;
import com.example.cart_service.services.CartRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserCartUseCaseImpl implements GetUserCartUseCase {
    private final CartRedisService cartRedisService;
    private final Logger logger = LoggerFactory.getLogger(GetUserCartUseCaseImpl.class);

    @Override
    public List<ProductDto> execute(String userId) {
        try {
            return cartRedisService.getProductsFromCart(userId);
        } catch (Exception e) {
            logger.error("Error occurred while getting user cart: {}", e);
            throw new RuntimeException(e);
        }
    }

}
