package com.khahnm04.controller;

import com.khahnm04.dto.request.UserCreationRequest;
import com.khahnm04.dto.request.UserUpdateRequest;
import com.khahnm04.dto.response.ApiResponse;
import com.khahnm04.dto.response.UserDetailResponse;
import com.khahnm04.dto.response.UserProfileResponse;
import com.khahnm04.enums.Status;
import com.khahnm04.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserProfileResponse> createUser(
        @Valid @RequestPart("data") UserCreationRequest request,
        @RequestPart(value = "avatar", required = false) MultipartFile file
    ) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.createUser(request, file))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserProfileResponse> getUserById(
        @PathVariable Long id
    ) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.getUserById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserDetailResponse>> getAllUsers() {
        return ApiResponse.<List<UserDetailResponse>>builder()
                .data(userService.getAllUsers())
                .build();
    }

    @PutMapping
    public ApiResponse<UserProfileResponse> updateUser(
        Long id,
        @Valid @RequestBody UserUpdateRequest request
    ) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.updateUser(id, request))
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> changeUserStatus(
        @PathVariable Long id,
        @RequestParam("status") Status status
    ) {
        userService.changeUserStatus(id, status);
        return ApiResponse.builder()
                .message("User status has been changed")
                .build();
    }

}
