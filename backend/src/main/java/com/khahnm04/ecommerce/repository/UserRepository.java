package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE :identifier IN (u.email, u.phoneNumber, u.username)")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

}
