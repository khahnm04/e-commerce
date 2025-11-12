package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.catalog.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>, QuerydslPredicateExecutor<Brand> {
}
