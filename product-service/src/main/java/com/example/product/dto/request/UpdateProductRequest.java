package com.example.product.dto.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateProductRequest {
    private String slug;
    private String title;
    private String description;
    private String tags;
    private BigDecimal price;
    private byte categoryId;
    private byte vendorId;
    private List<String> imageUrls;
}
