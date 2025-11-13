package com.khahnm04.ecommerce.dto.request.brand;

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
public class BrandRequest implements Serializable {

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    @NotBlank(message = "name cannot be blank")
    private String name;

    private String description;

    private String country;

    @ValidEnum(name = "status", enumClass = StatusEnum.class)
    private String status;

}
