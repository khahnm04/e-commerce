package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.dto.request.ProfileRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.ProfileResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.service.contract.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> createUser(
        @Valid @RequestPart("data") UserRequest request,
        @RequestPart(value = "avatar", required = false) MultipartFile file
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(request, file))
                .message("user created")
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt,desc") List<String> sort,
        @RequestParam(required = false) String... search
    ) {
        PageResponse<UserResponse> pageResponse = userService.getAllUsers(page, size, sort, search);
        return ApiResponse.<List<UserResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all users successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(
        @PathVariable Long id
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(id))
                .message("get user by id successfully")
                .build();
    }

    @GetMapping("/profile")
    public ApiResponse<UserResponse> getProfile() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getProfile())
                .message("get my info successfully")
                .build();
    }

    @PutMapping("/profile/update")
    public ApiResponse<ProfileResponse> updateProfile(
        @Valid @RequestBody ProfileRequest request
    ) {
        return ApiResponse.<ProfileResponse>builder()
                .data(userService.updateProfile(request))
                .message("update my info successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UserRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(id, request))
                .message("update user successfully")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> updateUserStatus(
        @PathVariable Long id,
        @RequestParam("status") StatusEnum status
    ) {
        userService.updateUserStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("user status has been changed")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> softDeleteUser(
        @PathVariable Long id
    ) {
        userService.softDeleteUser(id);
        return ApiResponse.<Void>builder()
                .message("user soft deleted")
                .build();
    }

}
