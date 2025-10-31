package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.parent.id = :parentId")
    boolean existsByParent(@Param("parentId") Long id);

}
