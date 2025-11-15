package com.khahnm04.ecommerce.modules.brand.response;

import com.khahnm04.ecommerce.shared.common.enums.StatusEnum;
import com.khahnm04.ecommerce.shared.dto.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse extends BaseResponse<Long> {

    private String slug;
    private String name;
    private String description;
    private String country;
    private String logo;
    private StatusEnum status;

}
