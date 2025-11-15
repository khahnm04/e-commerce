package com.khahnm04.ecommerce.modules.brand.application.mapper;

import com.khahnm04.ecommerce.modules.brand.request.BrandRequest;
import com.khahnm04.ecommerce.modules.brand.response.BrandResponse;
import com.khahnm04.ecommerce.modules.brand.domain.entity.Brand;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Brand fromBrandRequesttoBrand(BrandRequest request);

    @Mapping(target = "logo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBrand(@MappingTarget Brand brand, BrandRequest request);

    BrandResponse toBrandResponse(Brand brand);

}
