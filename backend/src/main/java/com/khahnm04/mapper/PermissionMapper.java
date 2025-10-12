package com.khahnm04.mapper;

import com.khahnm04.dto.request.PermissionRequest;
import com.khahnm04.dto.response.PermissionResponse;
import com.khahnm04.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
