package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.auth.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByName(String name);

    Optional<Permission> findByName(String name);
}
