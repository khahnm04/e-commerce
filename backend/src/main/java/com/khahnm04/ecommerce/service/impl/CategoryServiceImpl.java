package com.khahnm04.ecommerce.service.impl;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.entity.Category;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.CategoryMapper;
import com.khahnm04.ecommerce.repository.CategoryRepository;
import com.khahnm04.ecommerce.service.CloudinaryService;
import com.khahnm04.ecommerce.service.contract.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request, MultipartFile file) {
        Category category = categoryMapper.toCategory(request);

        if (!StringUtils.hasText(category.getName())) {
            Category categoryParent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParent(categoryParent);
        }
        category.setImage(cloudinaryService.upload(file));
        
        try {
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        
        CategoryResponse response = categoryMapper.toCategoryResponse(category);
        if (category.getParent() != null) {
            response.setParentId(category.getParent().getId());
        }
        return response;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile file) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (categoryRepository.existsByName(request.getName()) && !request.getName().equals(category.getName())) {
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);
        }

        if (request.getParentId() != null) {
            Category categoryParent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParent(categoryParent);
        }
        categoryMapper.updateCategory(category, request);
        category.setImage(cloudinaryService.upload(file));

        CategoryResponse response = categoryMapper.toCategoryResponse(categoryRepository.save(category));
        response.setParentId(category.getParent().getId());
        return response;
    }

    @Override
    public void softDeleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (categoryRepository.existsByParent(id)) {
            throw new AppException(ErrorCode.CATEGORY_HAS_CHILDREN);
        }

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
