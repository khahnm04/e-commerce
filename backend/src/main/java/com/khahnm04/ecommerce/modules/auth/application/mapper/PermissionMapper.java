package com.khahnm04.ecommerce.modules.auth.application.mapper;

import com.khahnm04.ecommerce.modules.auth.application.request.PermissionRequest;
import com.khahnm04.ecommerce.modules.auth.application.response.PermissionResponse;
import com.khahnm04.ecommerce.modules.auth.domain.entity.Permission;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);

}
