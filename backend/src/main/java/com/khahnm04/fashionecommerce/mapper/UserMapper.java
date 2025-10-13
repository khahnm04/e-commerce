package com.khahnm04.fashionecommerce.mapper;

import com.khahnm04.fashionecommerce.dto.request.UserCreationRequest;
import com.khahnm04.fashionecommerce.dto.request.UserUpdateRequest;
import com.khahnm04.fashionecommerce.dto.response.UserDetailResponse;
import com.khahnm04.fashionecommerce.dto.response.UserProfileResponse;
import com.khahnm04.fashionecommerce.entity.User;
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
