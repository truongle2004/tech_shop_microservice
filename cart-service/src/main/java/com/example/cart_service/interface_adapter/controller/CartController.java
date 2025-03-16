package com.example.cart_service.interface_adapter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cart_service.application.usecase.cart.AddCartItemUseCase;
import com.example.cart_service.application.usecase.cart.DeleteAllCartUseCase;
import com.example.cart_service.application.usecase.cart.DeleteCartUseCase;
import com.example.cart_service.application.usecase.cart.GetUserCartUseCase;
import com.example.cart_service.application.usecase.cart.UpdateQuantityUseCase;
import com.example.cart_service.dto.model.ProductDto;
import com.example.cart_service.dto.request.CartItemRequest;
import com.example.cart_service.dto.request.DeleteCartRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final AddCartItemUseCase addCartItemUseCase;
    private final GetUserCartUseCase getUserCartUseCase;
    private final UpdateQuantityUseCase updateQuantityUseCase;
    private final DeleteAllCartUseCase deleteAllCartUseCase;
    private final DeleteCartUseCase deleteCartUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductDto>> getUserCart(@PathVariable String userId) {
        return new ResponseEntity<>(
                this.getUserCartUseCase.execute(userId),
                HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addProductToCart(@RequestBody CartItemRequest item) {
        this.addCartItemUseCase.execute(item);
        return new ResponseEntity<>(
                Map.of("message", "Product added to cart successfully"),
                HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<Map<String, String>> updateProductQuantity(@RequestBody CartItemRequest item) {
        this.updateQuantityUseCase.execute(item.getUserId(), item.getQuantity(), item.getProductId());
        return new ResponseEntity<>(
                Map.of("message", "Product quantity updated successfully"),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Map<String, String>> deleteAllCartItem(@PathVariable String userId) {
        this.deleteAllCartUseCase.execute(userId);
        return new ResponseEntity<>(
                Map.of("message", "Cart deleted successfully"),
                HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteAllCartItem(@RequestBody DeleteCartRequest request) {
        this.deleteCartUseCase.execute(request.getUserId(), request.getListProductId());
        return new ResponseEntity<>(
                Map.of("message", "Product deleted successfully"),
                HttpStatus.NO_CONTENT);
    }
}
