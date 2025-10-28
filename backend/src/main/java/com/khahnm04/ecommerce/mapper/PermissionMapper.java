package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.PermissionRequest;
import com.khahnm04.ecommerce.dto.response.PermissionResponse;
import com.khahnm04.ecommerce.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
