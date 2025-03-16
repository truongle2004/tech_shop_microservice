package com.example.cart_service.dto.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductDto {
    private short id;
    private String title;
    private int quantity;
    private List<ImageDto> images;
    private BigDecimal price;
}
