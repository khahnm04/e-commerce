package com.khahnm04.shopco.service.brand;

import com.khahnm04.shopco.model.dto.request.BrandRequest;
import com.khahnm04.shopco.model.dto.response.BrandResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IBrandService {

    BrandResponse createBrand(BrandRequest request, MultipartFile logoFile);

    BrandResponse getBrand(String slug);

    List<BrandResponse> getBrands();

    BrandResponse updateBrand(String slug, BrandRequest request, MultipartFile logoFile);

    void deleteBrand(String slug);

}
