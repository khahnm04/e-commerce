package com.khahnm04.ecommerce.modules.category.request;

import com.khahnm04.ecommerce.shared.common.enums.CategoryStatusEnum;
import com.khahnm04.ecommerce.shared.common.validation.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile image;

    @ValidEnum(name = "status", enumClass = CategoryStatusEnum.class)
    private String status;

    private Long parentId;

}
