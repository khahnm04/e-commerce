package com.khahnm04.ecommerce.service.banner;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.banner.BannerRequest;
import com.khahnm04.ecommerce.dto.response.banner.BannerResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.entity.banner.Banner;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.BannerMapper;
import com.khahnm04.ecommerce.repository.BannerRepository;
import com.khahnm04.ecommerce.service.upload.CloudinaryService;
import com.khahnm04.ecommerce.common.util.SortUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public BannerResponse createBanner(BannerRequest request) {
        if (bannerRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.BANNER_EXISTED);
        }
        if (bannerRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.BANNER_EXISTED);
        }

        Banner banner = bannerMapper.fromBannerRequestToBanner(request);
        banner.setImage(cloudinaryService.upload(request.getImage()));

        Banner savedBanner = bannerRepository.save(banner);
        log.info("Banner created with id = {}", savedBanner.getId());
        return bannerMapper.toBannerResponse(savedBanner);
    }

    @Override
    public PageResponse<BannerResponse> getAllBanners(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Banner> bannerPage = bannerRepository.findAllByDeletedAtIsNull(pageable);
        Page<BannerResponse> dtoPage = bannerPage.map(bannerMapper::toBannerResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<BannerResponse> getAllDeletedBanners(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Banner> bannerPage = bannerRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<BannerResponse> dtoPage = bannerPage.map(bannerMapper::toBannerResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public BannerResponse getBannerDetailById(Long id) {
        return bannerMapper.toBannerResponse(getBannerById(id));
    }

    @Override
    public BannerResponse getBannerDetailBySlug(String slug) {
        Banner banner = bannerRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.BANNER_NOT_FOUND));
        return bannerMapper.toBannerResponse(banner);
    }

    @Override
    public BannerResponse updateBanner(Long id, BannerRequest request, MultipartFile file) {
        Banner banner = getBannerById(id);

        if (!Objects.equals(banner.getName(), request.getName())
                && bannerRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.BANNER_EXISTED);
        }
        if (!Objects.equals(banner.getSlug(), request.getSlug())
                && bannerRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.BANNER_EXISTED);
        }

        bannerMapper.updateBanner(banner, request);
        banner.setImage(cloudinaryService.upload(file));

        Banner savedBanner = bannerRepository.save(banner);
        log.info("Updated banner with id = {}", savedBanner.getId());
        return bannerMapper.toBannerResponse(savedBanner);
    }

    @Override
    public void updateBannerStatus(Long id, String status) {
        Banner banner = getBannerById(id);

        boolean isValid = Arrays.stream(StatusEnum.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(status));
        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }

        banner.setStatus(StatusEnum.valueOf(status));
        bannerRepository.save(banner);
        log.info("Updated status banner with id = {}", banner.getId());
    }

    @Override
    public void softDeleteBanner(Long id) {
        Banner banner = getBannerById(id);
        banner.setDeletedAt(LocalDateTime.now());
        bannerRepository.save(banner);
        log.info("Soft deleted banner with id {}", id);
    }

    @Override
    public void deleteBanner(Long id) {
        Banner banner = getBannerById(id);
        bannerRepository.delete(banner);
        log.info("Deleted banner with id {}", id);
    }

    @Override
    public void restoreBanner(Long id) {
        Banner banner = getBannerById(id);
        banner.setDeletedAt(null);
        bannerRepository.save(banner);
        log.info("Banner restored with id {}", id);
    }

    private Banner getBannerById(Long id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BANNER_NOT_FOUND));
    }

}
