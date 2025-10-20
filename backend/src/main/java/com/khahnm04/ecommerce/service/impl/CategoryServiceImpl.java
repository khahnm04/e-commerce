package com.khahnm04.ecommerce.service.impl;

import com.khahnm04.ecommerce.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.entity.Category;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.CategoryMapper;
import com.khahnm04.ecommerce.repository.CategoryRepository;
import com.khahnm04.ecommerce.service.contract.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);
        }
        Category category = categoryMapper.toCategory(request);
        Category categoryParent = categoryRepository.findByParentId(request.getParentId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_PARENT_NOT_EXISTED));
        category.setParent(categoryParent);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryMapper.updateCategory(category, request);
        Category categoryParent = categoryRepository.findByParentId(request.getParentId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_PARENT_NOT_EXISTED));
        category.setParent(categoryParent);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void softDeleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
        log.info("Category with id {} has been soft deleted", id);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
        log.info("Category with id {} has been deleted", id);
    }

}
