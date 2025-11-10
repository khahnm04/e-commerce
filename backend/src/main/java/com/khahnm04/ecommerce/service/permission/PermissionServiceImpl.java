package com.khahnm04.ecommerce.service.permission;

import com.khahnm04.ecommerce.dto.request.PermissionRequest;
import com.khahnm04.ecommerce.dto.response.PermissionResponse;
import com.khahnm04.ecommerce.entity.Permission;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.PermissionMapper;
import com.khahnm04.ecommerce.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        log.info("Permission created successfully with id = {}", permission.getId());
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    @Override
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        Permission permission = getPermissionById(id);
        if (!Objects.equals(permission.getName(), request.getName()) && permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        permissionMapper.updatePermission(permission, request);
        permission = permissionRepository.save(permission);
        log.info("Permission updated successfully with id = {}", id);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public void deletePermission(Long id) {
        getPermissionById(id);
        permissionRepository.deleteById(id);
        log.info("Permission deleted successfully with id = {}", id);
    }

    private Permission getPermissionById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
    }

}
