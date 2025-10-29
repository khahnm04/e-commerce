package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.QUser;
import com.khahnm04.ecommerce.entity.User;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

    @Override
    default void customize(QuerydslBindings bindings, QUser user) {
        bindings.bind(user.username).first(StringExpression::containsIgnoreCase);
        bindings.bind(user.email).first(StringExpression::containsIgnoreCase);
        bindings.bind(user.phoneNumber).first(StringExpression::contains);
        bindings.bind(user.status).first(SimpleExpression::eq);
        bindings.bind(user.gender).first(SimpleExpression::eq);
    }


    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.phoneNumber = :identifier")
    Optional<User> findByPhoneNumberOrEmail(@Param("identifier") String identifier);

}
