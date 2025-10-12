package com.khahnm04.controller;

import com.khahnm04.dto.request.PermissionRequest;
import com.khahnm04.dto.response.ApiResponse;
import com.khahnm04.dto.response.PermissionResponse;
import com.khahnm04.service.PermissionService;
import com.khahnm04.service.impl.PermissionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createUser(
        @RequestBody @Valid PermissionRequest request
    ) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.createPermission(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(
        @PathVariable String permission
    ) {
        permissionService.deletePermission(permission);
        return ApiResponse.<Void>builder().build();
    }

}
