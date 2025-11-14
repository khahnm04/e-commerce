package com.khahnm04.ecommerce.dto.response.banner;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.response.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BannerResponse extends BaseResponse<Long> {

    private String slug;
    private String name;
    private String image;
    private StatusEnum status;

}
