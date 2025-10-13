package com.khahnm04.fashionecommerce.mapper;

import com.khahnm04.fashionecommerce.dto.request.RoleRequest;
import com.khahnm04.fashionecommerce.dto.response.RoleResponse;
import com.khahnm04.fashionecommerce.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
