package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.banner.BannerRequest;
import com.khahnm04.ecommerce.dto.response.banner.BannerResponse;
import com.khahnm04.ecommerce.entity.banner.Banner;
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
