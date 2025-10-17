package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.dto.request.UserCreationRequest;
import com.khahnm04.ecommerce.dto.request.UserUpdateRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.UserDetailResponse;
import com.khahnm04.ecommerce.dto.response.UserProfileResponse;
import com.khahnm04.ecommerce.constant.StatusEnum;
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
    public ApiResponse<UserProfileResponse> createUser(
        @Valid @RequestPart("data") UserCreationRequest request,
        @RequestPart(value = "avatar", required = false) MultipartFile file
    ) {
        return ApiResponse.<UserProfileResponse>builder()
                .message("user created")
                .data(userService.createUser(request, file))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserDetailResponse>> getAllUsers() {
        return ApiResponse.<List<UserDetailResponse>>builder()
                .data(userService.getAllUsers())
                .message("get all users")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserProfileResponse> getUserById(
        @PathVariable Long id
    ) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.getUserById(id))
                .message("get user by id")
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserProfileResponse> getMyInfo() {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.getMyInfo())
                .message("get my info")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDetailResponse> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UserUpdateRequest request
    ) {
        return ApiResponse.<UserDetailResponse>builder()
                .data(userService.updateUser(id, request))
                .message("update user")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> changeUserStatus(
        @PathVariable Long id,
        @RequestParam("status") StatusEnum status
    ) {
        userService.changeUserStatus(id, status);
        return ApiResponse.builder()
                .message("user status has been changed")
                .build();
    }

}
