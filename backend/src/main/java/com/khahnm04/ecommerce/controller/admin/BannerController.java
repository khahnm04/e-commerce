package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.banner.BannerRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.banner.BannerResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.service.banner.BannerService;
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
@RequestMapping("${api.prefix}/admin/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BannerResponse> createBanner(
            @Valid BannerRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.createBanner(request, file))
                .message("banner created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<BannerResponse>> getAllBanners(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<BannerResponse> pageResponse = bannerService.getAllBanners(page - 1, size, sort);
        return ApiResponse.<List<BannerResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all banners successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<BannerResponse>> getAllDeletedBanners(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<BannerResponse> pageResponse = bannerService.getAllDeletedBanners(page - 1, size, sort);
        return ApiResponse.<List<BannerResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all soft deleted banners successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BannerResponse> getBannerDetailById(
            @PathVariable Long id
    ) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.getBannerDetailById(id))
                .message("get banner detail by id successfully")
                .build();
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<BannerResponse> getBannerDetailBySlug(
            @PathVariable String slug
    ) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.getBannerDetailBySlug(slug))
                .message("get banner detail by slug successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<BannerResponse> updateBanner(
            @PathVariable Long id,
            @Valid BannerRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.updateBanner(id, request, file))
                .message("updated banner")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateBannerStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        bannerService.updateBannerStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("banner status updated successfully")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteBanner(
            @PathVariable Long id
    ) {
        bannerService.softDeleteBanner(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted banner")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBanner(
            @PathVariable Long id
    ) {
        bannerService.deleteBanner(id);
        return ApiResponse.<Void>builder()
                .message("deleted banner")
                .build();
    }

}
