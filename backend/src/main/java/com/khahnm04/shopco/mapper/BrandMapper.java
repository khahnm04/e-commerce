package com.khahnm04.shopco.mapper;

import com.khahnm04.shopco.model.dto.request.BrandRequest;
import com.khahnm04.shopco.model.dto.response.BrandResponse;
import com.khahnm04.shopco.model.entity.Brand;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(
        target = "status",
        expression = "java(request.getStatus() != null ? request.getStatus() : com.khahnm04.shopco.common.enums.Status.ACTIVE)"
    )
    Brand toBrand(BrandRequest request);

    BrandResponse toBrandResponse(Brand brand);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBrandFromRequest(@MappingTarget Brand brand, BrandRequest request);

}
