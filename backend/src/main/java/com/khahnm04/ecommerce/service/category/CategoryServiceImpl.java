package com.khahnm04.ecommerce.service.category;

import com.khahnm04.ecommerce.common.enums.CategoryStatusEnum;
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
import org.springframework.util.StringUtils;
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

        String path = Optional.ofNullable(category.getParent())
                    .map(Category::getPath)
                    .orElse("");

        Category savedCategory = categoryRepository.save(category);
        savedCategory.setPath(path + savedCategory.getId() + "/");
        savedCategory = categoryRepository.save(savedCategory);

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
    @Transactional
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

        Long currentParentId = (category.getParent() != null) ? category.getParent().getId() : null;
        Long newParentId = request.getParentId();

        if (Objects.equals(currentParentId, newParentId)) {
            Category savedCategory = categoryRepository.save(category);
            log.info("Updated category (no parent change) with id {}", savedCategory.getId());
            return categoryMapper.toCategoryResponse(savedCategory);
        } else {
            String oldPath = category.getPath();
            String newPath = category.getId() + "/";
            Category newParent = null;

            if (newParentId != null) {
                newParent = getCategoryById(newParentId);
                if (newParent.getPath().startsWith(oldPath)) {
                    throw new AppException(ErrorCode.CANNOT_MOVE_PARENT_TO_CHILD);
                }
                newPath = newParent.getPath() + newPath;
            }

            category.setParent(newParent);
            category.setPath(newPath);

            Category savedCategory = categoryRepository.save(category);
            categoryRepository.updateDescendantPaths(oldPath + "%", newPath, oldPath.length());

            log.info("Updated category AND MOVED tree with id {}", savedCategory.getId());
            return categoryMapper.toCategoryResponse(savedCategory);
        }
    }

    @Override
    @Transactional
    public void updateCategoryStatus(Long id, String status) {
        Category category = getCategoryById(id);

        boolean isValid = Arrays.stream(CategoryStatusEnum.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(status));
        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }

        String pathWithWildcard = category.getPath() + "%";

        if (CategoryStatusEnum.INACTIVE.name().equalsIgnoreCase(status)) {
            category.setStatus(CategoryStatusEnum.INACTIVE_MANUAL);
            categoryRepository.save(category);
            categoryRepository.updateDescendantStatusesByPath(
                    pathWithWildcard, category.getId(), CategoryStatusEnum.INACTIVE_CASCADE, CategoryStatusEnum.ACTIVE);
        } else if (CategoryStatusEnum.ACTIVE.name().equalsIgnoreCase(status)) {
            category.setStatus(CategoryStatusEnum.ACTIVE);
            categoryRepository.save(category);
            categoryRepository.updateDescendantStatusesByPath(
                    pathWithWildcard, category.getId(), CategoryStatusEnum.ACTIVE, CategoryStatusEnum.INACTIVE_CASCADE);
        } else {
            throw new RuntimeException("status action is invalid!");
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

}
