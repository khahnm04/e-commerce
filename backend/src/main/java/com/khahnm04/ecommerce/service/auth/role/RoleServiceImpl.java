package com.khahnm04.ecommerce.service.auth.role;

import com.khahnm04.ecommerce.dto.request.auth.RoleRequest;
import com.khahnm04.ecommerce.dto.response.auth.RoleResponse;
import com.khahnm04.ecommerce.entity.Permission;
import com.khahnm04.ecommerce.entity.Role;
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
import java.util.function.Function;
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
        validateRequest(request, null);
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
        validateRequest(request, role);
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

    private void validateRequest(RoleRequest request, Role existingRole) {
        Map<Function<RoleRequest, Boolean>, ErrorCode> validators = new LinkedHashMap<>();
        validators.put(r -> CollectionUtils.isEmpty(r.getPermissions()), ErrorCode.PERMISSION_REQUIRED);
        if (existingRole == null) {
            validators.put(r -> roleRepository.existsByName(r.getName()), ErrorCode.ROLE_EXISTED);
        } else {
            validators.put(r -> !Objects.equals(existingRole.getName(), r.getName())
                    && roleRepository.existsByName(r.getName()), ErrorCode.ROLE_EXISTED);
        }
        validators.forEach((predicate, error) -> {
            if (predicate.apply(request)) {
                throw new AppException(error);
            }
        });
    }

    private void assignPermissionsToRole(Role role, Set<String> permissionNames) {
        Set<Permission> permissions = Optional.ofNullable(permissionNames)
                .orElseGet(Collections::emptySet)
                .stream()
                .map(permissionName -> permissionRepository.findByName(permissionName)
                        .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);
    }

}
