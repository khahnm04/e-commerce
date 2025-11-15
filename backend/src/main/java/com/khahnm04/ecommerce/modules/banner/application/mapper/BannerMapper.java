package com.khahnm04.ecommerce.modules.banner.application.mapper;

import com.khahnm04.ecommerce.modules.banner.request.BannerRequest;
import com.khahnm04.ecommerce.modules.banner.response.BannerResponse;
import com.khahnm04.ecommerce.modules.banner.domain.entity.Banner;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Banner fromBannerRequestToBanner(BannerRequest request);

    @Mapping(target = "image", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBanner(@MappingTarget Banner banner, BannerRequest request);

    BannerResponse toBannerResponse(Banner banner);

}
