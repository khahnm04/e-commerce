package com.khahnm04.ecommerce.service.user;

import com.khahnm04.ecommerce.dto.request.user.AddressUserRequest;
import com.khahnm04.ecommerce.dto.request.user.ChangePasswordRequest;
import com.khahnm04.ecommerce.dto.request.user.ProfileRequest;
import com.khahnm04.ecommerce.dto.request.user.UserRequest;
import com.khahnm04.ecommerce.dto.response.user.AddressUserResponse;
import com.khahnm04.ecommerce.dto.response.user.ProfileResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.user.UserResponse;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface UserService {

    // admin
    UserResponse createUser(UserRequest request, MultipartFile file);
    PageResponse<UserResponse> getAllUsers(int page, int size, String sort);
    PageResponse<UserResponse> getAllDeletedUsers(int page, int size, String sort);
    UserResponse getUserDetailById(Long id);
    UserResponse updateUser(Long id, UserRequest request, MultipartFile file);
    void updateUserStatus(Long id, StatusEnum status);
    void updateUserRole(Long id, Set<Long> roles);
    void softDeleteUser(Long id);
    void restoreUser(Long id);

    // client
    ProfileResponse getProfile();
    ProfileResponse updateProfile(ProfileRequest request);
    void uploadAvatar(MultipartFile file);
    void changePassword(ChangePasswordRequest request);
    AddressUserResponse addAddress(AddressUserRequest request);
    List<AddressUserResponse> getAllAddresses();
    AddressUserResponse updateAddress(Long id, AddressUserRequest request);
    void deleteAddress(Long id);

}
