package com.khahnm04.shopco.mapper;

import com.khahnm04.shopco.dto.request.UserCreationRequest;
import com.khahnm04.shopco.dto.response.UserCreationResponse;
import com.khahnm04.shopco.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserCreationResponse toUserCreationResponse(User user);

}
