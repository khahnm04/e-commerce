package com.khahnm04.ecommerce.dto.response.product;

import com.khahnm04.ecommerce.common.enums.ProductStatusEnum;
import com.khahnm04.ecommerce.dto.response.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse extends BaseResponse<Long> {

    private String slug;
    private String name;
    private Long price;
    private Long oldPrice;
    private String image;
    private String description;
    private ProductStatusEnum status;
    private Long brandId;

}
