package com.khahnm04.ecommerce.service.category;

import com.khahnm04.ecommerce.dto.request.category.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.category.CategoryResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request, MultipartFile file);
    PageResponse<CategoryResponse> getAllCategories(int page, int size, String sort);
    PageResponse<CategoryResponse> getAllDeletedCategories(int page, int size, String sort);
    CategoryResponse getCategoryDetailById(Long id);
    CategoryResponse getCategoryDetailBySlug(String slug);
    CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile file);
    void updateCategoryStatus(Long id, String status);
    void softDeleteCategory(Long id);
    void deleteCategory(Long id);

}
