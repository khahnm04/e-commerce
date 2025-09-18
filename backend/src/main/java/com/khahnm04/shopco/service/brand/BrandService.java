package com.khahnm04.shopco.service.brand;

import com.khahnm04.shopco.common.enums.ErrorCode;
import com.khahnm04.shopco.exception.AppException;
import com.khahnm04.shopco.mapper.BrandMapper;
import com.khahnm04.shopco.model.dto.request.BrandRequest;
import com.khahnm04.shopco.model.dto.response.BrandResponse;
import com.khahnm04.shopco.model.entity.Brand;
import com.khahnm04.shopco.repository.BrandRepository;
import com.khahnm04.shopco.service.cloudinary.IFileUploadService;
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
    public BrandResponse createBrand(BrandRequest request, MultipartFile logoFile) {
        Brand existingBrand = brandRepository.findBySlug(request.getSlug());
        if (existingBrand != null) {
            throw new AppException(ErrorCode.BRAND_EXISTED);
        }
        if (logoFile != null && !logoFile.isEmpty()) {
            String logoUrl = cloudinaryService.uploadFile(logoFile, "shopco-files/brands");
            request.setLogoUrl(logoUrl);
        }
        Brand brand = brandMapper.toBrand(request);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toBrandResponse(savedBrand);
    }

    @Override
    public BrandResponse getBrand(Integer id) {
        return null;
    }

    @Override
    public List<BrandResponse> getBrands() {
        return List.of();
    }

    @Override
    public BrandResponse updateBrand(Integer id, BrandRequest request) {
        return null;
    }

    @Override
    public BrandResponse deleteBrand(Integer id) {
        return null;
    }

}
