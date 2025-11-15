package com.khahnm04.ecommerce.modules.banner.response;

import com.khahnm04.ecommerce.shared.common.enums.StatusEnum;
import com.khahnm04.ecommerce.shared.dto.BaseResponse;
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
