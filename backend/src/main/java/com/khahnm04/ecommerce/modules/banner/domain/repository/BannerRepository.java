package com.khahnm04.ecommerce.modules.banner.domain.repository;

import com.khahnm04.ecommerce.modules.banner.domain.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    boolean existsBySlug(String slug);

    boolean existsByName(String name);

    Page<Banner> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Banner> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<Banner> findBySlug(String slug);

}
