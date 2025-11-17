package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.common.enums.ProductStatusEnum;
import com.khahnm04.ecommerce.common.util.SortUtils;
import com.khahnm04.ecommerce.dto.request.product.ProductRequest;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.entity.brand.Brand;
import com.khahnm04.ecommerce.entity.product.Product;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.ProductMapper;
import com.khahnm04.ecommerce.repository.BrandRepository;
import com.khahnm04.ecommerce.repository.ProductRepository;
import com.khahnm04.ecommerce.service.upload.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }
        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        Product product = productMapper.fromProductRequestToProduct(request);
        product.setBrand(brand);
        product.setImage(cloudinaryService.upload(request.getImage()));

        Product savedProduct = productRepository.save(product);
        log.info("Product created with id = {}", savedProduct.getId());
        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Product> productPage = productRepository.findAllByDeletedAtIsNull(pageable);
        Page<ProductResponse> dtoPage = productPage.map(productMapper::toProductResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<ProductResponse> getAllDeletedProducts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Product> productPage = productRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<ProductResponse> dtoPage = productPage.map(productMapper::toProductResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public ProductResponse getProductDetailById(Long id) {
        return productMapper.toProductResponse(getProductById(id));
    }

    @Override
    public ProductResponse getProductDetailBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request, MultipartFile file) {
        Product product = getProductById(id);

        if (!Objects.equals(product.getName(), request.getName())
                && productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }
        if (!Objects.equals(product.getSlug(), request.getSlug())
                && productRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        productMapper.updateProduct(product, request);
        product.setBrand(brand);
        product.setImage(cloudinaryService.upload(file));

        Product savedProduct = productRepository.save(product);
        log.info("Updated product with id = {}", savedProduct.getId());
        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    public void updateProductStatus(Long id, String status) {
        Product product = getProductById(id);

        boolean isValid = Arrays.stream(ProductStatusEnum.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(status));
        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }

        product.setStatus(ProductStatusEnum.valueOf(status));
        productRepository.save(product);
        log.info("Updated status product with id = {}", id);
    }

    @Override
    public void softDeleteProduct(Long id) {
        Product product = getProductById(id);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
        log.info("Soft deleted product with id {}", id);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        log.info("Deleted product with id {}", id);
    }

    @Override
    public void restoreProduct(Long id) {
        Product product = getProductById(id);
        product.setDeletedAt(null);
        productRepository.save(product);
        log.info("Restored product with id {}", id);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

}
