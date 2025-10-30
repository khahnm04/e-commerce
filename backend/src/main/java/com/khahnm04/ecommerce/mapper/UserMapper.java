package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.AddressUserRequest;
import com.khahnm04.ecommerce.dto.request.ProfileRequest;
import com.khahnm04.ecommerce.dto.request.RegisterRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.ProfileResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.entity.Address;
import com.khahnm04.ecommerce.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User toUser(UserRequest request) {
        if (request == null) return null;
        User user = User.builder()
                .gender(request.getGender() == null ? GenderEnum.UNKNOWN : request.getGender())
                .status(request.getStatus() == null ? StatusEnum.ACTIVE : request.getStatus())
                .build();

        if (request.getGender() == null) request.setGender(GenderEnum.UNKNOWN);
        if (request.getStatus() == null) request.setStatus(StatusEnum.ACTIVE);

        updateUser(user, request);
        return user;
    }

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfile(@MappingTarget User user, ProfileRequest request);

    ProfileResponse toProfileResponse(User user);

    User fromRegisterRequestToUser(RegisterRequest request);

}
