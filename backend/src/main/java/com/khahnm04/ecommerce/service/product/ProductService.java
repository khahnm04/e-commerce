package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.dto.request.product.ProductRequest;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);
    PageResponse<ProductResponse> getAllProducts(int page, int size, String sort);
    PageResponse<ProductResponse> getAllDeletedProducts(int page, int size, String sort);
    ProductResponse getProductDetailById(Long id);
    ProductResponse getProductDetailBySlug(String slug);
    ProductResponse updateProduct(Long id, ProductRequest request, MultipartFile file);
    void updateProductStatus(Long id, String status);
    void softDeleteProduct(Long id);
    void deleteProduct(Long id);
    void restoreProduct(Long id);

}
