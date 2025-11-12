package com.khahnm04.ecommerce.service.catalog.brand;

import com.khahnm04.ecommerce.dto.request.catalog.BrandRequest;
import com.khahnm04.ecommerce.dto.response.catalog.BrandResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BrandService {

    BrandResponse createBrand(BrandRequest request, MultipartFile file);
    PageResponse<BrandResponse> getAllBrands(int page, int size, String sort, String... search);
    BrandResponse updateBrand(Long id, BrandRequest request, MultipartFile file);
    void softDeleteBrand(Long id);
    void deleteBrand(Long id);

}
