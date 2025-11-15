package com.khahnm04.ecommerce.modules.category.response;

import com.khahnm04.ecommerce.shared.common.enums.CategoryStatusEnum;
import com.khahnm04.ecommerce.shared.dto.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse extends BaseResponse<Long> {

    private String slug;
    private String name;
    private String description;
    private String image;
    private Long parentId;
    private String path;
    private CategoryStatusEnum status;

}
