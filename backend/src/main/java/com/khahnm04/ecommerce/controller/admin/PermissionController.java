package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.auth.PermissionRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.auth.PermissionResponse;
import com.khahnm04.ecommerce.service.auth.permission.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(
        @Valid @RequestBody PermissionRequest request
    ) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.createPermission(request))
                .message("permission created successfully")
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAllPermissions())
                .message("get all permissions successfully")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<PermissionResponse> updatePermission(
        @PathVariable Long id,
        @Valid @RequestBody PermissionRequest request
    ) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.updatePermission(id, request))
                .message("permission updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deletePermission(
        @PathVariable Long id
    ) {
        permissionService.deletePermission(id);
        return ApiResponse.<Void>builder()
                .message("permission deleted successfully")
                .build();
    }

}
