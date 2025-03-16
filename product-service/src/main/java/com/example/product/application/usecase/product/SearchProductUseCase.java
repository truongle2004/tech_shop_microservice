package com.example.product.application.usecase.product;

import java.util.List;

import com.example.product.dto.model.SearchResponseDto;

public interface SearchProductUseCase {
    List<SearchResponseDto> execute(String query);
}
