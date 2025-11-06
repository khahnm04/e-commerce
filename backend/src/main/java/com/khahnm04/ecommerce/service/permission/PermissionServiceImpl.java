package com.khahnm04.ecommerce.service.permission;

import com.khahnm04.ecommerce.dto.request.PermissionRequest;
import com.khahnm04.ecommerce.dto.response.PermissionResponse;
import com.khahnm04.ecommerce.entity.Permission;
import com.khahnm04.ecommerce.mapper.PermissionMapper;
import com.khahnm04.ecommerce.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void deletePermission(String permission) {
        permissionRepository.deleteById(permission);
    }

}
