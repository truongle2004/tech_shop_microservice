package com.example.cart_service.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.cart_service.dto.model.ImageDto;
import com.example.cart_service.dto.model.ProductDto;
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
    public void deleteProductInCart(String userId, List<Integer> listProductId) {
        String key = "cart:user-" + userId;

        if (!this.hashExist(key, "product")) {
            return;
        }

        for (Integer productId : listProductId) {
            this.delete(key, String.valueOf(productId));
        }
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
    public void updateProductInCart(String userId, int quantity, int productId) {
        String key = "cart:user-" + userId;

        Map<String, Object> products = this.getField(key);

        for (Map.Entry<String, Object> entry : products.entrySet()) {
            String[] arrKey = entry.getKey().split(":"); // e.g., "product:1"

            if (arrKey.length > 1 && Integer.parseInt(arrKey[1]) == productId) {

                Object updatedQuantity = (entry.getValue() instanceof Integer) ? quantity : String.valueOf(quantity);

                // Update Redis
                this.hashSet(key, entry.getKey(), updatedQuantity);
                break;
            }
        }
    }

    @Override
    public List<ProductDto> getProductsFromCart(String userId) {
        String key = "cart:user-" + userId;

        Map<String, Object> products = this.getField(key);

        List<ProductDto> listProductDtos = new ArrayList<>();

        for (Map.Entry<String, Object> entry : products.entrySet()) {
            // split string by ":" to get product id (for example product:1)
            String[] arrKey = entry.getKey().split(":");

            // Get product information by calling product service
            ProductDto productDto = getProductById(Integer.parseInt(arrKey[1]));

            if (productDto != null) {
                int quantity = (int) this.hashGet(key, entry.getKey());

                productDto.setQuantity(quantity);

                List<ImageDto> images = productDto.getImages();

                if (images != null && images.size() > 0) {
                    // Get only first image
                    productDto.setImages(List.of(images.get(0)));
                }

                listProductDtos.add(productDto);

            }
        }

        // sort by product title
        listProductDtos.sort(Comparator.comparing(ProductDto::getTitle));

        return listProductDtos;
    }

    /**
     * get product by id (call product service)
     *
     * @param productId
     * @return {@link ProductDto}
     */
    public ProductDto getProductById(int productId) {
        return this.webClient.get()
                .uri("http://localhost:8081/api/v1/products/" + productId)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

    @Override
    public void deleteAllCart(String userId) {
        this.delete("cart:user-" + userId);
    }

}
