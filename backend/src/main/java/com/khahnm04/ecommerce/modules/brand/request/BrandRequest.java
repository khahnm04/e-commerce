package com.khahnm04.ecommerce.modules.brand.request;

import com.khahnm04.ecommerce.shared.common.enums.StatusEnum;
import com.khahnm04.ecommerce.shared.common.validation.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile logo;

    private String country;

    @ValidEnum(name = "status", enumClass = StatusEnum.class)
    private String status;

}
