package com.example.product.application.usecase.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.product.application.exceptions.NotFoundException;
import com.example.product.application.usecase.product.DeleteProductUseCase;
import com.example.product.infra.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(DeleteProductUseCaseImpl.class);

    @Override
    public void execute(short id) {
        try {

            if (!this.productRepository.existsById(id)) {
                throw new NotFoundException("Product not found, please try again later.");
            }

            this.productRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error occurred while deleting product: {}", e);
            throw new RuntimeException("Failed to delete product. Please try again later.", e);
        }
    }

}
