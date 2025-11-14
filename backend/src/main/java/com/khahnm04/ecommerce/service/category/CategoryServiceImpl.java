package com.khahnm04.ecommerce.service.category;

import com.khahnm04.ecommerce.common.enums.CategoryStatusEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.common.util.SortUtils;
import com.khahnm04.ecommerce.dto.request.category.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.category.CategoryResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.entity.category.Category;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.CategoryMapper;
import com.khahnm04.ecommerce.repository.CategoryRepository;
import com.khahnm04.ecommerce.service.upload.CloudinaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request, MultipartFile file) {
        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.fromCategoryRequestToCategory(request);
        category.setImage(cloudinaryService.upload(file));
        category.setParent(Optional.ofNullable(request.getParentId())
                        .map(this::getCategoryById)
                        .orElse(null));

        Category savedCategory = categoryRepository.save(category);
        log.info("Saved new category with id {}", savedCategory.getId());
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public PageResponse<CategoryResponse> getAllCategories(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Category> categoryPage = categoryRepository.findAllByDeletedAtIsNull(pageable);
        Page<CategoryResponse> dtoPage = categoryPage.map(categoryMapper::toCategoryResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<CategoryResponse> getAllDeletedCategories(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Category> categoryPage = categoryRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<CategoryResponse> dtoPage = categoryPage.map(categoryMapper::toCategoryResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public CategoryResponse getCategoryDetailById(Long id) {
        return categoryMapper.toCategoryResponse(getCategoryById(id));
    }

    @Override
    public CategoryResponse getCategoryDetailBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile file) {
        Category category = getCategoryById(id);

        if (!Objects.equals(category.getSlug(), request.getSlug()) && categoryRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        if (!Objects.equals(category.getName(), request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        categoryMapper.updateCategory(category, request);
        category.setImage(cloudinaryService.upload(file));
        category.setParent(Optional.ofNullable(request.getParentId())
                .map(this::getCategoryById)
                .orElse(null));

        Category savedCategory = categoryRepository.save(category);
        log.info("Updated address with id {}", savedCategory.getId());
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public void updateCategoryStatus(Long id, String status) {
        Category category = getCategoryById(id);

        boolean isValid = Arrays.stream(CategoryStatusEnum.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(status));
        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }

        CategoryStatusEnum newStatus = CategoryStatusEnum.valueOf(status.toUpperCase());

        if (category.getStatus() == newStatus) {
            log.warn("Category id = {} already has status = {}", id, newStatus);
            return;
        }

        category.setStatus(CategoryStatusEnum.valueOf(status));
        categoryRepository.save(category);
        log.info("Updated status for category id = {}: {}", category.getId(), newStatus);

        if (newStatus == CategoryStatusEnum.ACTIVE) {
            activateDescendants(category.getId());
        } else {
            inactivateDescendants(category.getId());
        }
    }

    @Override
    public void softDeleteCategory(Long id) {
        Category category = getCategoryById(id);

        if (categoryRepository.existsByParent(id)) {
            throw new AppException(ErrorCode.CATEGORY_HAS_CHILDREN);
        }

        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
        log.info("Category with id {} has been soft deleted", id);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
        log.info("Category with id {} has been deleted", id);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private void inactivateDescendants(Long parentId) {
        List<Category> activeChildren = categoryRepository
                .findByParentIdAndStatusAndDeletedAtIsNull(parentId, CategoryStatusEnum.ACTIVE);

        if (activeChildren.isEmpty()) {
            return;
        }

        for (Category child : activeChildren) {
            child.setStatus(CategoryStatusEnum.INACTIVE_CASCADE);
        }
        categoryRepository.saveAll(activeChildren);
        log.info("  -> Cascaded INACTIVE for {} children of parent id = {}", activeChildren.size(), parentId);

        for (Category child : activeChildren) {
            inactivateDescendants(child.getId());
        }
    }

    private void activateDescendants(Long parentId) {
        List<Category> cascadedChildren = categoryRepository
                .findByParentIdAndStatusAndDeletedAtIsNull(parentId, CategoryStatusEnum.INACTIVE_CASCADE);

        if (cascadedChildren.isEmpty()) {
            return;
        }

        for (Category child : cascadedChildren) {
            child.setStatus(CategoryStatusEnum.ACTIVE);
        }
        categoryRepository.saveAll(cascadedChildren);
        log.info("  -> Cascaded ACTIVE for {} children of parent id = {}", cascadedChildren.size(), parentId);

        for (Category child : cascadedChildren) {
            activateDescendants(child.getId());
        }
    }

}
