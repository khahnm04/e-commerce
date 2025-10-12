package com.khahnm04.controller;

import com.khahnm04.dto.request.RoleRequest;
import com.khahnm04.dto.response.ApiResponse;
import com.khahnm04.dto.response.RoleResponse;
import com.khahnm04.service.impl.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(
        @Valid @RequestBody RoleRequest request
    ) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.createRole(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> deleteRole(
        @PathVariable String role
    ) {
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder().build();
    }

}
