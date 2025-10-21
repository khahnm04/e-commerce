package com.khahnm04.ecommerce.service.impl;

import com.khahnm04.ecommerce.constant.StatusEnum;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public CategoryResponse createCategory(CategoryRequest request, MultipartFile file) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);
        }
        Category category = categoryMapper.toCategory(request);

        if (request.getParentId() != null) {
            Category categoryParent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParent(categoryParent);
        }
        category.setImage(cloudinaryService.uploadFileIfPresent(file));
        category.setStatus(request.getStatus() == null ? StatusEnum.ACTIVE : request.getStatus());

        Category savedCategory = categoryRepository.save(category);
        CategoryResponse response = categoryMapper.toCategoryResponse(savedCategory);
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

        if (categoryRepository.existsByName(request.getName())
                && !request.getName().equals(category.getName())) {
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);
        }

        if (request.getParentId() != null) {
            Category categoryParent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParent(categoryParent);
        }
        categoryMapper.updateCategory(category, request);
        category.setImage(cloudinaryService.uploadFileIfPresent(file));

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
