package com.khahnm04.ecommerce.service.permission;

import com.khahnm04.ecommerce.dto.request.PermissionRequest;
import com.khahnm04.ecommerce.dto.response.PermissionResponse;
import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    PermissionResponse updatePermission(Long id, PermissionRequest request);
    void deletePermission(Long id);

}
