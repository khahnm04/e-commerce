package com.khahnm04.ecommerce.dto.response.brand;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.response.BaseResponse;
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
