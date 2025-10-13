package com.khahnm04.fashionecommerce.service;

import com.khahnm04.fashionecommerce.dto.request.UserCreationRequest;
import com.khahnm04.fashionecommerce.dto.request.UserUpdateRequest;
import com.khahnm04.fashionecommerce.dto.response.UserDetailResponse;
import com.khahnm04.fashionecommerce.dto.response.UserProfileResponse;
import com.khahnm04.fashionecommerce.constant.StatusEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserProfileResponse createUser(UserCreationRequest request, MultipartFile file);
    List<UserDetailResponse> getAllUsers();
    UserProfileResponse getUserById(Long id);
    UserProfileResponse getMyInfo();
    UserDetailResponse updateUser(Long id, UserUpdateRequest request);
    void changeUserStatus(Long id, StatusEnum status);

}
