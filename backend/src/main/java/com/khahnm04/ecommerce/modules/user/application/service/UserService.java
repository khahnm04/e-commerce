package com.khahnm04.ecommerce.modules.user.application.service;

import com.khahnm04.ecommerce.modules.user.application.request.AddressUserRequest;
import com.khahnm04.ecommerce.modules.user.application.request.ChangePasswordRequest;
import com.khahnm04.ecommerce.modules.user.application.request.UserProfileRequest;
import com.khahnm04.ecommerce.modules.user.application.request.UserRequest;
import com.khahnm04.ecommerce.modules.user.application.response.AddressUserResponse;
import com.khahnm04.ecommerce.modules.user.application.response.UserProfileResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.user.application.response.UserResponse;
import com.khahnm04.ecommerce.shared.common.enums.StatusEnum;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;

public interface UserService {

    // admin
    UserResponse createUser(UserRequest request);
    PageResponse<UserResponse> getAllUsers(int page, int size, String sort);
    PageResponse<UserResponse> getAllDeletedUsers(int page, int size, String sort);
    List<AddressUserResponse> getAllAddressesByUserId(Long id);
    UserResponse getUserDetailById(Long id);
    UserResponse updateUser(Long id, UserRequest request, MultipartFile file);
    void updateUserStatus(Long id, StatusEnum status);
    void updateUserRole(Long id, Set<Long> roles);
    void softDeleteUser(Long id);
    void restoreUser(Long id);

    // client
    UserProfileResponse getProfile();
    UserProfileResponse updateProfile(UserProfileRequest request);
    void uploadAvatar(MultipartFile file);
    void changePassword(ChangePasswordRequest request);
    AddressUserResponse addAddress(AddressUserRequest request);
    List<AddressUserResponse> getAllAddresses();
    AddressUserResponse updateAddress(Long id, AddressUserRequest request);
    void deleteAddress(Long id);

}
