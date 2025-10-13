package com.khahnm04.fashionecommerce.mapper;

import com.khahnm04.fashionecommerce.dto.request.PermissionRequest;
import com.khahnm04.fashionecommerce.dto.response.PermissionResponse;
import com.khahnm04.fashionecommerce.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
