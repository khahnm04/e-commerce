package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.UserCreationRequest;
import com.khahnm04.ecommerce.dto.request.UserUpdateRequest;
import com.khahnm04.ecommerce.dto.response.UserDetailResponse;
import com.khahnm04.ecommerce.dto.response.UserProfileResponse;
import com.khahnm04.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserProfileResponse toUserProfileResponse(User user);

    UserDetailResponse toUserDetailResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
