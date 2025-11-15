package com.khahnm04.ecommerce.modules.banner.application.service;

import com.khahnm04.ecommerce.modules.banner.request.BannerRequest;
import com.khahnm04.ecommerce.modules.banner.response.BannerResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {

    BannerResponse createBanner(BannerRequest request);
    PageResponse<BannerResponse> getAllBanners(int page, int size, String sort);
    PageResponse<BannerResponse> getAllDeletedBanners(int page, int size, String sort);
    BannerResponse getBannerDetailById(Long id);
    BannerResponse getBannerDetailBySlug(String slug);
    BannerResponse updateBanner(Long id, BannerRequest request, MultipartFile file);
    void updateBannerStatus(Long id, String status);
    void softDeleteBanner(Long id);
    void deleteBanner(Long id);
    void restoreBanner(Long id);

}
