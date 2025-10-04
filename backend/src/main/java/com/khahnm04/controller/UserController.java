package com.khahnm04.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khahnm04.dto.request.UserCreationRequest;
import com.khahnm04.dto.response.ApiResponse;
import com.khahnm04.dto.response.UserProfileResponse;
import com.khahnm04.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserProfileResponse> createUser(
        @Valid @RequestPart("data") UserCreationRequest request,
        @RequestPart(value = "avatar", required = false) MultipartFile file
    ) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.createUser(request, file))
                .build();
    }

}
