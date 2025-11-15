package com.khahnm04.ecommerce.modules.banner.api;

import com.khahnm04.ecommerce.modules.banner.request.BannerRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.banner.response.BannerResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.banner.application.service.BannerService;
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
            @Valid @ModelAttribute BannerRequest request
    ) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.createBanner(request))
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

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreBanner(@PathVariable Long id) {
        bannerService.restoreBanner(id);
        return ApiResponse.<Void>builder()
                .message("banner restored successfully")
                .build();
    }

}
