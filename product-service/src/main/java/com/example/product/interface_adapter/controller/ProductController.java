package com.example.product.interface_adapter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.application.usecase.product.DeleteProductUseCase;
import com.example.product.application.usecase.product.GetAllCategoryUseCase;
import com.example.product.application.usecase.product.GetByCategoryUseCase;
import com.example.product.application.usecase.product.GetProductByIdUseCase;
import com.example.product.application.usecase.product.SearchProductUseCase;
import com.example.product.application.usecase.product.UpdateProductUseCase;
import com.example.product.dto.model.CategoryDto;
import com.example.product.dto.model.ProductDto;
import com.example.product.dto.model.SearchResponseDto;
import com.example.product.dto.request.UpdateProductRequest;
import com.example.product.dto.response.ObjectResponse;
import com.example.product.utils.AppConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final GetAllCategoryUseCase getAllCategoryUseCase;
    private final GetByCategoryUseCase getByCategoryUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final SearchProductUseCase searchProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    @GetMapping
    public ResponseEntity<ObjectResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "category", required = false) String category) {
        return new ResponseEntity<>(this.getByCategoryUseCase.execute(pageNo, pageSize, sortBy, category),
                HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(this.getAllCategoryUseCase.execute(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable short id) {
        return new ResponseEntity<>(this.getProductByIdUseCase.execute(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchResponseDto>> searchProduct(@RequestBody String query) {
        return new ResponseEntity<>(this.searchProductUseCase.execute(query), HttpStatus.OK);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getProductSuggestions() {
        return new ResponseEntity<>(List.of("laptop-gaming", "chuot", "ban-phim", "vo-may-tinh", "man-hinh"),
                HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable short productId) {
        this.deleteProductUseCase.execute(productId);
        return new ResponseEntity<>(Map.of("message", "Product deleted successfully"), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateProduct(@PathVariable short id,
            @RequestBody UpdateProductRequest updateProductRequest) {
        this.updateProductUseCase.execute(id, updateProductRequest);
        return new ResponseEntity<>(Map.of("message", "Product updated successfully"), HttpStatus.OK);
    }

}
