package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    List<Category> findAllByDeletedAtIsNull();

    @Query("""
        SELECT COUNT(c) > 0 FROM Category c
        WHERE c.parent.id = :parentId
    """)
    boolean existsByParent(@Param("parentId") Long id);

}
