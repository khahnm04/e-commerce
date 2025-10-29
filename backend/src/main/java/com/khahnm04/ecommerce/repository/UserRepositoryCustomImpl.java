package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.entity.QUser;
import com.khahnm04.ecommerce.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserResponse> findUsersDynamic(String search, Pageable pageable) {
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        if (search != null && !search.trim().isEmpty()) {
            String searchTerm = search.trim().toLowerCase();
            builder.and(
                    user.username.lower().contains(searchTerm)
                            .or(user.email.lower().contains(searchTerm))
                            .or(user.phoneNumber.contains(searchTerm))
            );
        }

        List<User> users = queryFactory
                .selectFrom(user)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(buildOrderSpecifiers(pageable.getSort(), user))
                .fetch();

        // Convert sang UserResponse DTO
        List<UserResponse> content = users.stream()
                .map(this::convertToUserResponse)
                .toList();

        Long total = queryFactory
                .select(user.count())
                .from(user)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private OrderSpecifier<?>[] buildOrderSpecifiers(Sort sort, QUser user) {
        PathBuilder<?> entityPath = new PathBuilder<>(user.getType(), user.getMetadata());
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            orderSpecifiers.add(
                    new OrderSpecifier<>(direction, entityPath.getComparable(property, Comparable.class))
            );
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private UserResponse convertToUserResponse(User userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .fullName(userEntity.getFullName())
                .dateOfBirth(userEntity.getDateOfBirth())
                .gender(userEntity.getGender())
                .image(userEntity.getImage())
                .status(userEntity.getStatus())
                .lastLoginAt(userEntity.getLastLoginAt())
                .createdAt(userEntity.getCreatedAt())
                .createdBy(userEntity.getCreatedBy())
                .updatedAt(userEntity.getUpdatedAt())
                .updatedBy(userEntity.getUpdatedBy())
                .deletedAt(userEntity.getDeletedAt())
                .build();
    }

}
