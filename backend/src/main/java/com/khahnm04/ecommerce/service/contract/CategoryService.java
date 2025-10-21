package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request, MultipartFile file);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile file);
    void softDeleteCategory(Long id);
    void deleteCategory(Long id);

}
