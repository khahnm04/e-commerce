package com.khahnm04.ecommerce.modules.auth.domain.repository;

import com.khahnm04.ecommerce.modules.auth.domain.entity.RedisToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisTokenRepository extends CrudRepository<RedisToken, String> {
}
