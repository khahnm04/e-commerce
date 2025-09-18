package com.khahnm04.shopco.controller;

import com.khahnm04.shopco.model.dto.request.BrandRequest;
import com.khahnm04.shopco.model.dto.response.ApiResponse;
import com.khahnm04.shopco.model.dto.response.BrandResponse;
import com.khahnm04.shopco.service.brand.IBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/brands")
public class BrandController {

    private final IBrandService brandService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ApiResponse<BrandResponse> createBrand(
        @Valid @ModelAttribute BrandRequest request,
        @RequestPart(value = "logo_url", required = false) MultipartFile logoFile
    ) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.createBrand(request, logoFile))
                .build();
    }

}
