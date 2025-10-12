package com.khahnm04.mapper;

import com.khahnm04.dto.request.RoleRequest;
import com.khahnm04.dto.response.RoleResponse;
import com.khahnm04.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
