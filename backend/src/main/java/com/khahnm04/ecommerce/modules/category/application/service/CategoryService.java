package com.khahnm04.ecommerce.modules.category.application.service;

import com.khahnm04.ecommerce.modules.category.request.CategoryRequest;
import com.khahnm04.ecommerce.modules.category.response.CategoryResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);
    PageResponse<CategoryResponse> getAllCategories(int page, int size, String sort);
    PageResponse<CategoryResponse> getAllDeletedCategories(int page, int size, String sort);
    CategoryResponse getCategoryDetailById(Long id);
    CategoryResponse getCategoryDetailBySlug(String slug);
    CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile file);
    void updateCategoryStatus(Long id, String status);
    void softDeleteCategory(Long id);
    void deleteCategory(Long id);
    void restoreCategory(Long id);

}
