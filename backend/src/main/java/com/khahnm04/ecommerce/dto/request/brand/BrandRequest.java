package com.khahnm04.ecommerce.dto.request.brand;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest implements Serializable {

    @NotBlank(message = "name cannot be blank")
    private String name;

    private String description;

    private String country;

    private String logo;

    private StatusEnum status;

}
