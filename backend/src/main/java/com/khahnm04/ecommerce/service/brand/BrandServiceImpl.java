package com.khahnm04.ecommerce.service.brand;

import com.khahnm04.ecommerce.dto.request.BrandRequest;
import com.khahnm04.ecommerce.dto.response.BrandResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.entity.Brand;
import com.khahnm04.ecommerce.entity.QBrand;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.BrandMapper;
import com.khahnm04.ecommerce.repository.BrandRepository;
import com.khahnm04.ecommerce.service.CloudinaryService;
import com.khahnm04.ecommerce.util.SortUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public BrandResponse createBrand(BrandRequest request, MultipartFile file) {
        Brand brand = brandMapper.toBrand(request);
        brand.setLogo(cloudinaryService.upload(file));
        Brand savedBrand = saveBrand(brand);
        log.info("Saved new brand with id {}", savedBrand.getId());
        return brandMapper.toBrandResponse(savedBrand);
    }

    @Override
    public PageResponse<BrandResponse> getAllBrands(int page, int size, String sort, String... search) {
        Sort sortObj = SortUtils.parseSort(sort);
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size, sortObj);

        QBrand brand = QBrand.brand;
        BooleanBuilder builder = new BooleanBuilder();

        if (search != null && search.length > 0) {
            BooleanBuilder searchBuilder = new BooleanBuilder();
            Arrays.stream(search)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(String::toLowerCase)
                    .map(s -> s.matches("\\d{4}-\\d{2}-\\d{2} .*") && s.contains("T") ? s.replace("T", " ") : s)
                    .forEach(keyword -> searchBuilder.or(
                            brand.name.lower().contains(keyword)
                            .or(brand.description.lower().contains(keyword))
                            .or(brand.status.stringValue().lower().contains(keyword))
                            .or(brand.createdAt.stringValue().contains(keyword))
                            .or(brand.createdBy.stringValue().contains(keyword))
                            .or(brand.updatedAt.stringValue().contains(keyword))
                            .or(brand.updatedBy.stringValue().contains(keyword))
                            .or(brand.deletedAt.stringValue().contains(keyword))
                    ));
            builder.and(searchBuilder);
        }

        Page<Brand> brandPage = brandRepository.findAll(builder, pageable);
        Page<BrandResponse> dtoPage = brandPage.map(brandMapper::toBrandResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandRequest request, MultipartFile file) {
        Brand brand = getBrandById(id);
        brandMapper.updateBrand(brand, request);
        brand.setLogo(cloudinaryService.upload(file));
        Brand savedBrand = saveBrand(brand);
        log.info("Updated brand with id {}", savedBrand.getId());
        return brandMapper.toBrandResponse(savedBrand);
    }

    @Override
    public void softDeleteBrand(Long id) {
        Brand brand = getBrandById(id);
        brand.setDeletedAt(LocalDateTime.now());
        saveBrand(brand);
        log.info("Soft deleted brand with id {}", id);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand = getBrandById(id);
        brandRepository.delete(brand);
        log.info("Deleted brand with id {}", id);
    }

    private Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
    }

    private Brand saveBrand(Brand brand) {
        try {
            brandRepository.save(brand);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.BRAND_EXISTED);
        }
        return brand;
    }

}
