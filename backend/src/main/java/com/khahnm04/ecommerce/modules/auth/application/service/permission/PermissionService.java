package com.khahnm04.ecommerce.modules.auth.application.service.permission;

import com.khahnm04.ecommerce.modules.auth.application.request.PermissionRequest;
import com.khahnm04.ecommerce.modules.auth.application.response.PermissionResponse;
import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    PermissionResponse updatePermission(Long id, PermissionRequest request);
    void deletePermission(Long id);

}
