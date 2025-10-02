package com.khahnm04.shopco.controller;

import com.khahnm04.shopco.dto.request.UserCreationRequest;
import com.khahnm04.shopco.dto.response.ApiResponse;
import com.khahnm04.shopco.dto.response.UserCreationResponse;
import com.khahnm04.shopco.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ApiResponse<UserCreationResponse> createUser(
        @Valid @ModelAttribute UserCreationRequest request,
        @RequestPart(value = "avatar_url", required = false) MultipartFile file
    ) {
        return ApiResponse.<UserCreationResponse>builder()
                .result(userService.createUser(request, file))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserCreationResponse>> getUsers() {
        return ApiResponse.<List<UserCreationResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserCreationResponse> getUserById(
        @PathVariable Integer id
    ) {
        return ApiResponse.<UserCreationResponse>builder()
                .result(userService.getUserById(id))
                .build();
    }

}
