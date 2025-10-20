package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.service.contract.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(
        @RequestBody CategoryRequest categoryRequest
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.createCategory(categoryRequest))
                .message("category created")
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.getAllCategories())
                .message("get all categories")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategory(
        @PathVariable Long id,
        @RequestBody CategoryRequest request
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.updateCategory(id, request))
                .message("category created")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> softDeleteCategory(
        @PathVariable Long id
    ) {
        categoryService.softDeleteCategory(id);
        return ApiResponse.builder()
                .message("category soft deleted")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteCategory(
        @PathVariable Long id
    ) {
        categoryService.deleteCategory(id);
        return ApiResponse.builder()
                .message("category deleted")
                .build();
    }

}
