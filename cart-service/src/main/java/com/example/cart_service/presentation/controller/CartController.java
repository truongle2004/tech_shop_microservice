package com.example.cart_service.presentation.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cart_service.application.usecase.cart.AddCartItemUseCase;

import com.example.cart_service.dto.request.CartItemRequest;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final AddCartItemUseCase addCartItemUseCase;

    // @GetMapping("/{userId}")
    // public ResponseEntity<CartDto> getUserCart(@PathVariable String userId) {
    // return new ResponseEntity<>(
    // this.getUserCartUseCase.execute(userId),
    // HttpStatus.OK);
    // }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addProductToCart(@RequestBody CartItemRequest item) {
        this.addCartItemUseCase.execute(item);
        return new ResponseEntity<>(
                Map.of("message", "Product added to cart successfully"),
                HttpStatus.CREATED);
    }
}
