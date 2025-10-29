package com.khahnm04.ecommerce.dto.request;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest implements Serializable {

    @NotBlank(message = "CATEGORY_NUMBER_REQUIRED")
    private String name;

    private String description;
    private String image;
    private Long parentId;
    private StatusEnum status;

}
