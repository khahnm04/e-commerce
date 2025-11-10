package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.brand.BrandRequest;
import com.khahnm04.ecommerce.dto.response.brand.BrandResponse;
import com.khahnm04.ecommerce.entity.Brand;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    default Brand toBrand(BrandRequest request) {
        if (request == null) return null;
        Brand brand = Brand.builder()
                .status(StatusEnum.ACTIVE)
                .build();
        updateBrand(brand, request);
        return brand;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBrand(@MappingTarget Brand brand, BrandRequest request);

    BrandResponse toBrandResponse(Brand brand);

}
