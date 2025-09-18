package com.khahnm04.shopco.controller;

import com.khahnm04.shopco.model.dto.request.BrandRequest;
import com.khahnm04.shopco.model.dto.response.ApiResponse;
import com.khahnm04.shopco.model.dto.response.BrandResponse;
import com.khahnm04.shopco.service.brand.IBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/brands")
public class BrandController {

    private final IBrandService brandService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ApiResponse<BrandResponse> createBrand(
        @Valid @ModelAttribute BrandRequest request,
        @RequestPart(value = "logo_url", required = false) MultipartFile file
    ) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.createBrand(request, file))
                .build();
    }

    @GetMapping("/{slug}")
    public ApiResponse<BrandResponse> getBrand(
        @PathVariable String slug
    ) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.getBrand(slug))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<BrandResponse>> getBrands() {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getBrands())
                .build();
    }

    @PutMapping("/{slug}")
    public ApiResponse<BrandResponse> updateBrand(
        @PathVariable String slug,
        @ModelAttribute BrandRequest request,
        @RequestPart(value = "logo_url", required = false) MultipartFile file
    ) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.updateBrand(slug, request, file))
                .build();
    }

    @DeleteMapping("/{slug}")
    public ApiResponse<BrandResponse> deleteBrand(
        @PathVariable String slug
    ) {
        brandService.deleteBrand(slug);
        return ApiResponse.<BrandResponse>builder()
                .message("Brand deleted successfully")
                .build();
    }

}
