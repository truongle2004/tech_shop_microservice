package com.example.product.application.usecase.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.product.application.exceptions.NotFoundException;
import com.example.product.application.usecase.product.UpdateProductUseCase;
import com.example.product.dto.request.UpdateProductRequest;
import com.example.product.entities.CategoryEntity;
import com.example.product.entities.ProductEntity;
import com.example.product.entities.VendorEntity;
import com.example.product.infra.repositories.CategoryRepository;
import com.example.product.infra.repositories.ProductRepository;
import com.example.product.infra.repositories.VendorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateProductUseCaseImpl.class);

    @Override
    public void execute(short id, UpdateProductRequest updateProductRequest) {
        try {
            // Find existing product or throw NotFoundException
            ProductEntity product = productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

            // Update fields only if new values are provided
            if (updateProductRequest.getSlug() != null) {
                product.setSlug(updateProductRequest.getSlug());
            }
            if (updateProductRequest.getTitle() != null) {
                product.setTitle(updateProductRequest.getTitle());
            }
            if (updateProductRequest.getDescription() != null) {
                product.setDescription(updateProductRequest.getDescription());
            }
            if (updateProductRequest.getTags() != null) {
                product.setTags(updateProductRequest.getTags());
            }
            if (updateProductRequest.getPrice() != null) {
                product.setPrice(updateProductRequest.getPrice());
            }

            if (updateProductRequest.getCategoryId() > 0) {
                CategoryEntity category = categoryRepository.findById(updateProductRequest.getCategoryId())
                        .orElseThrow(() -> new NotFoundException(
                                "Category not found with ID: " + updateProductRequest.getCategoryId()));
                product.setCategoryEntity(category);
            }

            if (updateProductRequest.getVendorId() > 0) {
                VendorEntity vendor = vendorRepository.findById(updateProductRequest.getVendorId())
                        .orElseThrow(() -> new NotFoundException(
                                "Vendor not found with ID: " + updateProductRequest.getVendorId()));
                product.setVendorEntity(vendor);
            }

            productRepository.save(product);
        } catch (Exception e) {
            logger.error("Error occurred while updating product: {}", e);
            throw new RuntimeException("Failed to update product. Please try again later.", e);
        }
    }
}
