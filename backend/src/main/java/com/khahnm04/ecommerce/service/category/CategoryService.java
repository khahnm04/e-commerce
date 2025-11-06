package com.khahnm04.ecommerce.service.category;

import com.khahnm04.ecommerce.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request, MultipartFile file);
    PageResponse<CategoryResponse> getAllCategories(int page, int size, String sort, String... search);
    CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile file);
    void softDeleteCategory(Long id);
    void deleteCategory(Long id);

}
