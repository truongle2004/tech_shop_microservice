package com.example.product.application.usecase.product.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.product.application.usecase.product.GetAllCategoryUseCase;
import com.example.product.dto.model.CategoryDto;
import com.example.product.entities.CategoryEntity;
import com.example.product.infra.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllCategoryUseCaseImpl implements GetAllCategoryUseCase {

    private final CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(GetAllCategoryUseCaseImpl.class);

    @Override
    public List<CategoryDto> execute() {
        try {
            List<CategoryEntity> categories = categoryRepository.findAll();
            return categories.stream()
                    .map(category -> CategoryDto
                            .builder()
                            .id(category.getId())
                            .name(category.getName())
                            .slug(category.getSlug())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all categories: {}", e);
            throw new RuntimeException("Failed to fetch categories. Please try again later.", e);
        }
    }

}
