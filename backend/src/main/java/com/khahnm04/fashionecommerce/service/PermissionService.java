package com.khahnm04.fashionecommerce.service;

import com.khahnm04.fashionecommerce.dto.request.PermissionRequest;
import com.khahnm04.fashionecommerce.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    void deletePermission(String permission);

}
