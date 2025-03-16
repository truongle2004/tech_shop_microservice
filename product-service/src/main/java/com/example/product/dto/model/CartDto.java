package com.example.product.dto.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {
    private int id;
    private BigDecimal totalPrice;
    private List<CartItemDto> cartItems;
}
