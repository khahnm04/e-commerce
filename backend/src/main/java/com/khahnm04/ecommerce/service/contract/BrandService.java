package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.request.BrandRequest;
import com.khahnm04.ecommerce.dto.response.BrandResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrandService {

    BrandResponse createBrand(BrandRequest request, MultipartFile file);
    PageResponse<BrandResponse> getAllBrands(int page, int size, String sort, String... search);
    BrandResponse updateBrand(Long id, BrandRequest request, MultipartFile file);
    void softDeleteBrand(Long id);
    void deleteBrand(Long id);

}
