package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<UserResponse> findUsersDynamic(String search, Pageable pageable);

}
