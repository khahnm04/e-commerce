package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    default Category toCategory(CategoryRequest request) {
        if (request == null) return null;
        Category category = Category.builder()
                .status(StatusEnum.ACTIVE)
                .build();
        updateCategory(category, request);
        return category;
    }

    @Mapping(target = "parentId", ignore = true)
    CategoryResponse toCategoryResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategory(@MappingTarget Category category, CategoryRequest request);

}
