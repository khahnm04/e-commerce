package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.user.ProfileRequest;
import com.khahnm04.ecommerce.dto.request.auth.RegisterRequest;
import com.khahnm04.ecommerce.dto.request.user.UserRequest;
import com.khahnm04.ecommerce.dto.response.user.ProfileResponse;
import com.khahnm04.ecommerce.dto.response.user.UserResponse;
import com.khahnm04.ecommerce.entity.User;
import org.mapstruct.*;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User fromUserRequestToUser(UserRequest request) {
        if (request == null) return null;

        request.setGender(Objects.requireNonNullElse(request.getGender(), GenderEnum.UNKNOWN));
        request.setStatus(Objects.requireNonNullElse(request.getStatus(), StatusEnum.ACTIVE));

        User user = User.builder()
                .gender(request.getGender())
                .status(request.getStatus())
                .build();

        updateUser(user, request);
        return user;
    }

    @Mapping(target = "roles", ignore = true)
    UserResponse fromUserToUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfile(@MappingTarget User user, ProfileRequest request);

    ProfileResponse fromUserToProfileResponse(User user);

    User fromRegisterRequestToUser(RegisterRequest request);

    UserRequest fromProfileRequestToUserRequest(ProfileRequest request);

}
