package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.RegisterRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.MyInfoResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
    void updateUser(@MappingTarget User user, UserRequest request);

    MyInfoResponse toMyInfoResponse(User user);

    User fromRegisterRequestToUser(RegisterRequest request);

}
