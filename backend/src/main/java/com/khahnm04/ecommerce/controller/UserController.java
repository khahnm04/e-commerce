package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.dto.request.MyInfoRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.MyInfoResponse;
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
    public ApiResponse<PageResponse<UserResponse>> getAllUsers(
        @RequestParam(required = false) String search,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt,desc") List<String> sort
    ) {
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .data(userService.getAllUsers(search, page, size, sort))
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
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .message("get my info successfully")
                .build();
    }

    @PutMapping("/profile/update")
    public ApiResponse<MyInfoResponse> updateMyInfo(
        @Valid @RequestBody MyInfoRequest request
    ) {
        return ApiResponse.<MyInfoResponse>builder()
                .data(userService.updateMyInfo(request))
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
    public ApiResponse<Void> changeUserStatus(
        @PathVariable Long id,
        @RequestParam("status") StatusEnum status
    ) {
        userService.changeUserStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("user status has been changed")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> softDeleteUser(
        @PathVariable Long id
    ) {
        userService.softDeleteUserById(id);
        return ApiResponse.<Void>builder()
                .message("user soft deleted")
                .build();
    }

}
