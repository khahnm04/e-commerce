package com.khahnm04.ecommerce.service.user;

import com.khahnm04.ecommerce.dto.request.ProfileRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.ProfileResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponse createUser(UserRequest request, MultipartFile file);
    PageResponse<UserResponse> getAllUsers(int page, int size, String sort, String... search);
    UserResponse getUserById(Long id);
    UserResponse getProfile();
    ProfileResponse updateProfile(ProfileRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    void updateUserStatus(Long id, StatusEnum status);
    void softDeleteUser(Long id);

}
