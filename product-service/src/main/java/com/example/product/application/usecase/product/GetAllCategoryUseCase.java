package com.example.product.application.usecase.product;

import java.util.List;

import com.example.product.dto.model.CategoryDto;

public interface GetAllCategoryUseCase {
    List<CategoryDto> execute();
}