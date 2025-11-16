package com.khahnm04.ecommerce.service.auth.permission;

import com.khahnm04.ecommerce.dto.request.auth.PermissionRequest;
import com.khahnm04.ecommerce.dto.response.auth.PermissionResponse;
import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    PermissionResponse updatePermission(Long id, PermissionRequest request);
    void deletePermission(Long id);

}
