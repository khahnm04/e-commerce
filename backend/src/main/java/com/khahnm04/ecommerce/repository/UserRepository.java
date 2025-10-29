package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.phoneNumber = :identifier")
    Optional<User> findByPhoneNumberOrEmail(@Param("identifier") String identifier);

}
