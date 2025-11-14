package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.news.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, QuerydslPredicateExecutor<News> {

    boolean existsBySlug(String slug);

    Page<News> findAllByDeletedAtIsNull(Pageable pageable);

    Page<News> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<News> findBySlug(String slug);

}
