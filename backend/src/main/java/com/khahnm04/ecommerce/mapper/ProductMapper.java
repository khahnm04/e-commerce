package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.product.ProductRequest;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
import com.khahnm04.ecommerce.entity.product.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "brand", ignore = true)
    Product fromProductRequestToProduct(ProductRequest request);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduct(@MappingTarget Product product, ProductRequest request);

    @Mapping(target = "brandId", source = "brand.id")
    ProductResponse toProductResponse(Product product);
}
