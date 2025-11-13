package com.khahnm04.ecommerce.dto.request.category;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.validation.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest implements Serializable {

    @NotBlank(message = "category cannot be blank")
    private String name;

    private String description;

    private String image;

    private Long parentId;

    @ValidEnum(name = "status", enumClass = StatusEnum.class)
    private String status;

}
