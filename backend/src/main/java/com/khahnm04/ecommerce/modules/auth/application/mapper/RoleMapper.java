package com.khahnm04.ecommerce.modules.auth.application.mapper;

import com.khahnm04.ecommerce.modules.auth.application.request.RoleRequest;
import com.khahnm04.ecommerce.modules.auth.application.response.RoleResponse;
import com.khahnm04.ecommerce.modules.auth.domain.entity.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRole(@MappingTarget Role role, RoleRequest request);

}
