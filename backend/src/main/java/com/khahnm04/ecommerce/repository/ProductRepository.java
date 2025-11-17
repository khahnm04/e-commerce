package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySlug(String slug);

    boolean existsByName(String name);

    Page<Product> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Product> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<Product> findBySlug(String slug);

}
