package com.example.product.application.usecase.product;

import com.example.product.dto.model.ProductDto;
import com.example.product.dto.response.ObjectResponse;

public interface GetByCategoryUseCase {
    ObjectResponse<ProductDto> execute(int PageNo, int pageSize, String sortBy, String category);
}
