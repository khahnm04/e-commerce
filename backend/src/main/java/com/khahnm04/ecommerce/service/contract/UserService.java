package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.request.UserCreationRequest;
import com.khahnm04.ecommerce.dto.request.UserUpdateRequest;
import com.khahnm04.ecommerce.dto.response.UserDetailResponse;
import com.khahnm04.ecommerce.dto.response.UserProfileResponse;
import com.khahnm04.ecommerce.constant.StatusEnum;
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
