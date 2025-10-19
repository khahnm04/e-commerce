package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
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
    public ApiResponse<UserResponse> createUser(
        @Valid @RequestPart("data") UserRequest request,
        @RequestPart(value = "avatar", required = false) MultipartFile file
    ) {
        return ApiResponse.<UserResponse>builder()
                .message("user created")
                .data(userService.createUser(request, file))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAllUsers())
                .message("get all users")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(
        @PathVariable Long id
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(id))
                .message("get user by id")
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .message("get my info")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UserRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
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

    @DeleteMapping("/{id}")
    public ApiResponse<?> softDeleteUser(
        @PathVariable Long id
    ) {
        userService.softDeleteUserById(id);
        return ApiResponse.builder()
                .message("user soft deleted")
                .build();
    }

}
