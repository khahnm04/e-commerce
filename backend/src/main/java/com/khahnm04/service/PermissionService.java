package com.khahnm04.service;

import com.khahnm04.dto.request.PermissionRequest;
import com.khahnm04.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    void deletePermission(String permission);

}
