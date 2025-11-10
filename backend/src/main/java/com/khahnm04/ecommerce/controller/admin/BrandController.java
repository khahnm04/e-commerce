package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.brand.BrandRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.brand.BrandResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.service.brand.BrandService;
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
@RequestMapping("${api.prefix}/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BrandResponse> createBrand(
        @Valid @RequestPart("data") BrandRequest request,
        @RequestPart(value = "logo", required = false) MultipartFile file
    ) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.createBrand(request, file))
                .message("created brand")
                .build();
    }

    @GetMapping
    public ApiResponse<List<BrandResponse>> getAllUsers(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt,desc") String sort,
        @RequestParam(required = false) String... search
    ) {
        PageResponse<BrandResponse> pageResponse = brandService.getAllBrands(page, size, sort, search);
        return ApiResponse.<List<BrandResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all brands successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<BrandResponse> updateBrand(
        @PathVariable Long id,
        @Valid @RequestPart("data") BrandRequest request,
        @RequestPart(value = "logo", required = false) MultipartFile file
    ) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.updateBrand(id, request, file))
                .message("updated brand")
                .build();
    }

    @DeleteMapping("/{id}/soft")
    public ApiResponse<Void> softDeleteBrand(
        @PathVariable Long id
    ) {
        brandService.softDeleteBrand(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted brand")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBrand(
        @PathVariable Long id
    ) {
        brandService.deleteBrand(id);
        return ApiResponse.<Void>builder()
                .message("deleted brand")
                .build();
    }

}
