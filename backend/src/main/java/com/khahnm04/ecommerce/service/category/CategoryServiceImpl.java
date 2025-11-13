package com.khahnm04.ecommerce.service.category;

import com.khahnm04.ecommerce.dto.request.category.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.category.CategoryResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.entity.category.Category;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.CategoryMapper;
import com.khahnm04.ecommerce.repository.CategoryRepository;
import com.khahnm04.ecommerce.service.upload.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Optional;

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

        category.setImage(cloudinaryService.upload(file));
        category.setParent(Optional.ofNullable(request.getParentId())
                        .map(this::getCategoryById)
                        .orElse(null));

        Category savedCategory = saveCategory(category);
        log.info("Saved new category with id {}", savedCategory.getId());
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public PageResponse<CategoryResponse> getAllCategories(int page, int size, String sort, String... search) {
//        Sort sortObj = SortUtils.parseSort(sort);
//        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size, sortObj);
//
//        QCategory category = QCategory.category;
//        BooleanBuilder builder = new BooleanBuilder();
//
//        if (search != null && search.length > 0) {
//            BooleanBuilder searchBuilder = new BooleanBuilder();
//            Arrays.stream(search)
//                    .filter(Objects::nonNull)
//                    .map(String::trim)
//                    .filter(s -> !s.isEmpty())
//                    .map(String::toLowerCase)
//                    .map(s -> s.matches("\\d{4}-\\d{2}-\\d{2} .*") && s.contains("T") ? s.replace("T", " ") : s)
//                    .forEach(keyword -> searchBuilder.or(
//                            category.name.lower().contains(keyword)
//                            .or(category.description.lower().contains(keyword))
//                            .or(category.status.stringValue().lower().contains(keyword))
//                            .or(category.createdAt.stringValue().contains(keyword))
//                            .or(category.createdBy.stringValue().contains(keyword))
//                            .or(category.updatedAt.stringValue().contains(keyword))
//                            .or(category.updatedBy.stringValue().contains(keyword))
//                            .or(category.deletedAt.stringValue().contains(keyword))
//                    ));
//            builder.and(searchBuilder);
//        }
//
//        Page<Category> categoryPage = categoryRepository.findAll(builder, pageable);
//        Page<CategoryResponse> dtoPage = categoryPage.map(categoryMapper::toCategoryResponse);
//        return PageResponse.fromPage(dtoPage);
        return null;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile file) {
        Category category = getCategoryById(id);

        category.setImage(cloudinaryService.upload(file));
        category.setParent(Optional.ofNullable(request.getParentId())
                .map(this::getCategoryById)
                .orElse(null));

        categoryMapper.updateCategory(category, request);

        Category savedCategory = saveCategory(category);
        log.info("Updated address with id {}", savedCategory.getId());
        return categoryMapper.toCategoryResponse(savedCategory);
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

    private Category saveCategory(Category category) {
        try {
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        return category;
    }

}
