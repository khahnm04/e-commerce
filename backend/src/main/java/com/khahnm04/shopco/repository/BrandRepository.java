package com.khahnm04.shopco.repository;

import com.khahnm04.shopco.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Brand findBySlug(String slug);

}
