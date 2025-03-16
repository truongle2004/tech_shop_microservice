package com.example.cart_service.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.cart_service.dto.request.CartItemRequest;
import com.example.cart_service.services.CartRedisService;
import com.example.cart_service.services.base.impl.BaseRedisServiceImpl;

@Service
public class CartRedisServiceImpl extends BaseRedisServiceImpl implements CartRedisService {
    private final WebClient webClient;

    public CartRedisServiceImpl(RedisTemplate<String, Object> redisTemplate, WebClient webClient) {
        super(redisTemplate);
        this.webClient = webClient;
    }

    @Override
    public void addProductToCart(CartItemRequest cartItemRequest) {
        String key = "cart:user-" + cartItemRequest.getUserId();

        int updateQuantity;

        // use StringBuilder for better performance than String concatenation
        StringBuilder fieldKeyBuilder = new StringBuilder("product:");

        fieldKeyBuilder.append(cartItemRequest.getProductId());

        // convert StringBuilder to String for easier to operate
        String fieldKey = fieldKeyBuilder.toString();

        // check if hash exist in redis
        if (this.hashExist(cartItemRequest.getUserId(), fieldKey)) {
            updateQuantity = (Integer) this.hashGet(cartItemRequest.getUserId(), fieldKey)
                    + cartItemRequest.getQuantity();
        } else {
            updateQuantity = cartItemRequest.getQuantity();
        }

        this.hashSet(key, fieldKey, updateQuantity);
    }

    @Override
    public void updateProductInCart(String userId, short quantity, int productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductInCart'");
    }

    @Override
    public List<String> getProductsFromCart(String userId) {
        String key = "cart:user-" + userId;

        Map<String, Object> products = this.getField(key);

        List<String> fieldKeys = new ArrayList<>();

        for (Map.Entry<String, Object> entry : products.entrySet()) {
            fieldKeys.add(entry.getKey());
        }

        return fieldKeys;

    }

}
