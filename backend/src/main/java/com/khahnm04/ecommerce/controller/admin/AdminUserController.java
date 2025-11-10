package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> createUser(
            @Valid @RequestPart("data") UserRequest request,
            @RequestPart(value = "avatar", required = false) MultipartFile file
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(request, file))
                .message("user created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc")  String sortDir
    ) {
        PageResponse<UserResponse> pageResponse = userService.getAllUsers(page - 1, size, sortBy, sortDir);
        return ApiResponse.<List<UserResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get all users successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<UserResponse>> getAllDeletedUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc")  String sortDir
    ) {
        PageResponse<UserResponse> pageResponse = userService.getAllDeletedUsers(page - 1, size, sortBy, sortDir);
        return ApiResponse.<List<UserResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get all users successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(
            @PathVariable Long id
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserDetailById(id))
                .message("get user by id successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestPart("data") UserRequest request,
            @RequestPart(value = "avatar", required = false) MultipartFile file

    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(id, request, file))
                .message("user updated successfully")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateUserStatus(
            @PathVariable Long id,
            @RequestParam StatusEnum status
    ) {
        userService.updateUserStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("user status updated successfully")
                .build();
    }

    @PatchMapping("/{id}/role")
    public ApiResponse<Void> updateUserRole(
            @PathVariable Long id,
            @RequestParam Set<Long> roles
    ) {
        userService.updateUserRole(id, roles);
        return ApiResponse.<Void>builder()
                .message("user role updated successfully")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteUser(
            @PathVariable Long id
    ) {
        userService.softDeleteUser(id);
        return ApiResponse.<Void>builder()
                .message("user soft deleted successfully")
                .build();
    }

    @PostMapping("/{id}/restore")
    public ApiResponse<Void> restoreUser(
            @PathVariable Long id
    ) {
        userService.restoreUser(id);
        return ApiResponse.<Void>builder()
                .message("user restored successfully")
                .build();
    }

}
