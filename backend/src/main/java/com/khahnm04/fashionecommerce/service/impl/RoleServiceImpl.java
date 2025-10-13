package com.khahnm04.fashionecommerce.service.impl;

import com.khahnm04.fashionecommerce.dto.request.RoleRequest;
import com.khahnm04.fashionecommerce.dto.response.RoleResponse;
import com.khahnm04.fashionecommerce.entity.Permission;
import com.khahnm04.fashionecommerce.entity.Role;
import com.khahnm04.fashionecommerce.mapper.RoleMapper;
import com.khahnm04.fashionecommerce.repository.PermissionRepository;
import com.khahnm04.fashionecommerce.repository.RoleRepository;
import com.khahnm04.fashionecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void deleteRole(String role) {
        roleRepository.deleteById(role);
    }

}
