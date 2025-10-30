package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.dto.request.AddressUserRequest;
import com.khahnm04.ecommerce.dto.response.AddressUserResponse;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.service.contract.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/users/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ApiResponse<AddressUserResponse> addAddress(
        @Valid @RequestBody AddressUserRequest request
    ) {
        return ApiResponse.<AddressUserResponse>builder()
                .data(addressService.addAddress(request))
                .message("added address")
                .build();
    }

    @GetMapping
    public ApiResponse<List<AddressUserResponse>> getAllAddresses() {
        return ApiResponse.<List<AddressUserResponse>>builder()
                .data(addressService.getAllAddresses())
                .message("get all addresses successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AddressUserResponse> updateAddress(
        @PathVariable Long id,
        @Valid @RequestBody AddressUserRequest request
    ) {
        return ApiResponse.<AddressUserResponse>builder()
                .data(addressService.updateAddress(id, request))
                .message("updated address")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAddress(
            @PathVariable Long id
    ) {
        addressService.deleteAddress(id);
        return ApiResponse.<Void>builder()
                .message("deleted address")
                .build();
    }

}
