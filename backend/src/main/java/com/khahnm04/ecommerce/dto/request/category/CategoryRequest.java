package com.khahnm04.ecommerce.dto.request.category;

import com.khahnm04.ecommerce.common.enums.CategoryStatusEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.common.validation.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest implements Serializable {

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    @NotBlank(message = "name cannot be blank")
    private String name;

    private String description;

    @ValidEnum(name = "status", enumClass = CategoryStatusEnum.class)
    private String status;

    private Long parentId;

}
