package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.common.enums.CategoryStatusEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.entity.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {

    @Query("SELECT COUNT(c.parent.id) > 0 FROM Category c WHERE c.parent.id = :parentId")
    boolean existsByParent(@Param("parentId") Long id);

    boolean existsBySlug(String slug);

    boolean existsByName(String slug);

    Page<Category> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Category> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<Category> findBySlug(String slug);

    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId AND c.status = :status AND c.deletedAt IS NULL")
    List<Category> findByParentIdAndStatusAndDeletedAtIsNull(Long parentId, CategoryStatusEnum status);

}
