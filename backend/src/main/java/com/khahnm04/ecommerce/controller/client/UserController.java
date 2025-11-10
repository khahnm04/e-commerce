package com.khahnm04.ecommerce.controller.client;

import com.khahnm04.ecommerce.dto.request.AddressUserRequest;
import com.khahnm04.ecommerce.dto.request.ChangePasswordRequest;
import com.khahnm04.ecommerce.dto.request.ProfileRequest;
import com.khahnm04.ecommerce.dto.response.*;
import com.khahnm04.ecommerce.service.address.AddressService;
import com.khahnm04.ecommerce.service.user.UserService;
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

    private final UserService userService;
    private final AddressService addressService;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PatchMapping("/upload")
    public ApiResponse<Void> uploadAvatar(
            MultipartFile file
    ) {
        userService.uploadAvatar(file);
        return ApiResponse.<Void>builder()
                .message("upload avatar successfully")
                .build();
    }

    @PatchMapping("/change-password")
    public ApiResponse<Void> changePassword(
            @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(request);
        return ApiResponse.<Void>builder()
                .message("password changed successfully")
                .build();
    }

    @PostMapping("/address")
    public ApiResponse<AddressUserResponse> addAddress(
            @Valid @RequestBody AddressUserRequest request
    ) {
        return ApiResponse.<AddressUserResponse>builder()
                .data(addressService.addAddress(request))
                .message("address added successfully")
                .build();
    }

    @GetMapping("/address")
    public ApiResponse<List<AddressUserResponse>> getAllAddresses() {
        return ApiResponse.<List<AddressUserResponse>>builder()
                .data(addressService.getAllAddresses())
                .message("get all addresses successfully")
                .build();
    }

    @PutMapping("/address/{id}")
    public ApiResponse<AddressUserResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressUserRequest request
    ) {
        return ApiResponse.<AddressUserResponse>builder()
                .data(addressService.updateAddress(id, request))
                .message("address updated successfully")
                .build();
    }

    @DeleteMapping("/address/{id}")
    public ApiResponse<Void> deleteAddress(
            @PathVariable Long id
    ) {
        addressService.deleteAddress(id);
        return ApiResponse.<Void>builder()
                .message("address deleted successfully")
                .build();
    }

}
