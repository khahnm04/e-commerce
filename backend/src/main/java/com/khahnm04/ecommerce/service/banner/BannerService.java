package com.khahnm04.ecommerce.service.banner;

import com.khahnm04.ecommerce.dto.request.banner.BannerRequest;
import com.khahnm04.ecommerce.dto.response.banner.BannerResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {

    BannerResponse createBanner(BannerRequest request, MultipartFile file);
    PageResponse<BannerResponse> getAllBanners(int page, int size, String sort);
    PageResponse<BannerResponse> getAllDeletedBanners(int page, int size, String sort);
    BannerResponse getBannerDetailById(Long id);
    BannerResponse getBannerDetailBySlug(String slug);
    BannerResponse updateBanner(Long id, BannerRequest request, MultipartFile file);
    void updateBannerStatus(Long id, String status);
    void softDeleteBanner(Long id);
    void deleteBanner(Long id);

}
