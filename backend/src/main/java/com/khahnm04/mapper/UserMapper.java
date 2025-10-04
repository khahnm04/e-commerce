package com.khahnm04.mapper;

import com.khahnm04.dto.request.UserCreationRequest;
import com.khahnm04.dto.request.UserUpdateRequest;
import com.khahnm04.dto.response.UserDetailResponse;
import com.khahnm04.dto.response.UserProfileResponse;
import com.khahnm04.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserProfileResponse toUserProfileResponse(User user);

    UserDetailResponse toUserDetailResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
