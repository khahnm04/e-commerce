package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.common.enums.CategoryStatusEnum;
import com.khahnm04.ecommerce.entity.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query(value = """
        UPDATE Category c
        SET c.status = :newStatus
        WHERE c.path LIKE :parentPathWithWildcard
        AND c.id != :parentId
        AND c.status = :oldStatus
        AND c.deletedAt IS NULL
    """)
    void updateDescendantStatusesByPath(
            @Param("parentPathWithWildcard") String parentPathWithWildcard,
            @Param("parentId") Long parentId,
            @Param("newStatus") CategoryStatusEnum newStatus,
            @Param("oldStatus") CategoryStatusEnum oldStatus);

    @Modifying
    @Query(value = """
        UPDATE Category c
        SET c.path = CONCAT(:newPathPrefix, SUBSTRING(c.path, :oldPathPrefixLength + 1))
        WHERE c.path LIKE :oldPathPrefixWithWildcard
        AND c.deletedAt IS NULL
    """)
    void updateDescendantPaths(
            @Param("oldPathPrefixWithWildcard") String oldPathPrefixWithWildcard,
            @Param("newPathPrefix") String newPathPrefix,
            @Param("oldPathPrefixLength") int oldPathPrefixLength);

}
