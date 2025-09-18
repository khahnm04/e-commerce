package com.khahnm04.shopco.service.brand;

import com.khahnm04.shopco.common.enums.ErrorCode;
import com.khahnm04.shopco.common.enums.Status;
import com.khahnm04.shopco.exception.AppException;
import com.khahnm04.shopco.mapper.BrandMapper;
import com.khahnm04.shopco.model.dto.request.BrandRequest;
import com.khahnm04.shopco.model.dto.response.BrandResponse;
import com.khahnm04.shopco.model.entity.Brand;
import com.khahnm04.shopco.repository.BrandRepository;
import com.khahnm04.shopco.service.storage.IFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final IFileUploadService cloudinaryService;

    @Override
    public BrandResponse createBrand(BrandRequest request, MultipartFile file) {
        brandRepository.findBySlug(request.getSlug())
                .ifPresent(brand -> {
                    throw new AppException(ErrorCode.BRAND_EXISTED);
                });
        if (file != null && !file.isEmpty()) {
            String logoUrl = cloudinaryService.uploadFile(file, "shopco-files/brands");
            request.setLogoUrl(logoUrl);
        }
        Brand newBrand = brandMapper.toBrand(request);
        Brand savedBrand = brandRepository.save(newBrand);
        return brandMapper.toBrandResponse(savedBrand);
    }

    @Override
    public BrandResponse getBrand(String slug) {
        Brand brand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXIST));
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public List<BrandResponse> getBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toBrandResponse)
                .toList();
    }

    @Override
    public BrandResponse updateBrand(String slug, BrandRequest request, MultipartFile file) {
        Brand existingBrand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXIST));
        if (file != null && !file.isEmpty()) {
            String logoUrl = cloudinaryService.uploadFile(file, "shopco-files/brands");
            request.setLogoUrl(logoUrl);
        }
        brandMapper.updateBrandFromRequest(existingBrand, request);
        Brand savedBrand = brandRepository.save(existingBrand);
        return brandMapper.toBrandResponse(savedBrand);
    }

    @Override
    public void deleteBrand(String slug) {
        Brand existingBrand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXIST));
        existingBrand.setStatus(Status.INACTIVE);
        brandRepository.save(existingBrand);
    }

}
