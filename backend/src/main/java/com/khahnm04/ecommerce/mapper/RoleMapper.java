package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.RoleRequest;
import com.khahnm04.ecommerce.dto.response.RoleResponse;
import com.khahnm04.ecommerce.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
