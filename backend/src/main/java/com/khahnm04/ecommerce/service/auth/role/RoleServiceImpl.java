package com.khahnm04.ecommerce.service.auth.role;

import com.khahnm04.ecommerce.dto.request.auth.RoleRequest;
import com.khahnm04.ecommerce.dto.response.auth.RoleResponse;
import com.khahnm04.ecommerce.entity.auth.Permission;
import com.khahnm04.ecommerce.entity.auth.Role;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.RoleMapper;
import com.khahnm04.ecommerce.repository.PermissionRepository;
import com.khahnm04.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (CollectionUtils.isEmpty(request.getPermissions())) {
            throw new AppException(ErrorCode.PERMISSION_REQUIRED);
        }
        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Role role = roleMapper.toRole(request);
        assignPermissionsToRole(role, request.getPermissions());
        role = roleRepository.save(role);
        log.info("Role created with name = {}", role.getName());
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role role = getRoleById(id);
        if (CollectionUtils.isEmpty(request.getPermissions())) {
            throw new AppException(ErrorCode.PERMISSION_REQUIRED);
        }
        if (!Objects.equals(role.getName(), request.getName())
                && roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        roleMapper.updateRole(role, request);
        assignPermissionsToRole(role, request.getPermissions());
        role = roleRepository.save(role);
        log.info("Role updated successfully with id = {}", id);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public void deleteRole(Long id) {
        getRoleById(id);
        roleRepository.deleteById(id);
        log.info("Role deleted successfully with id = {}", id);
    }

    private Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

    private void assignPermissionsToRole(Role role, Set<Long> permissionIds) {
        Set<Permission> permissions = Optional.ofNullable(permissionIds)
                .orElseGet(Collections::emptySet)
                .stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);
    }

}
