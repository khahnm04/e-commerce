package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.service.contract.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryResponse> createCategory(
        @Valid @RequestPart("data") CategoryRequest request,
        @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.createCategory(request, file))
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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CategoryResponse> updateCategory(
        @PathVariable Long id,
        @Valid @RequestPart("data") CategoryRequest request,
        @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.updateCategory(id, request, file))
                .message("category created")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> softDeleteCategory(
        @PathVariable Long id
    ) {
        categoryService.softDeleteCategory(id);
        return ApiResponse.<Void>builder()
                .message("category soft deleted")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(
        @PathVariable Long id
    ) {
        categoryService.deleteCategory(id);
        return ApiResponse.<Void>builder()
                .message("category deleted")
                .build();
    }

}
