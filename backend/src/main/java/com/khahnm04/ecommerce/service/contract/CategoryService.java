package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void softDeleteCategory(Long id);
    void deleteCategory(Long id);

}
