package com.khahnm04.ecommerce.dto.request.product;

import com.khahnm04.ecommerce.common.enums.ProductStatusEnum;
import com.khahnm04.ecommerce.common.validation.enums.ValidEnum;
import com.khahnm04.ecommerce.common.validation.upload.NotEmptyFile;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price must be >= 0")
    private Long price;

    @NotNull(message = "oldPrice is required")
    @Min(value = 0, message = "oldPrice must be >= 0")
    private Long oldPrice;

    @NotEmptyFile(message = "image cannot be blank")
    private MultipartFile image;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @ValidEnum(name = "status", enumClass = ProductStatusEnum.class)
    private String status;

    private Long brandId;

}
