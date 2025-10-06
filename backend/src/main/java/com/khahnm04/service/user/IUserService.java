package com.khahnm04.service.user;

import com.khahnm04.dto.request.UserCreationRequest;
import com.khahnm04.dto.request.UserUpdateRequest;
import com.khahnm04.dto.response.UserDetailResponse;
import com.khahnm04.dto.response.UserProfileResponse;
import com.khahnm04.enums.Status;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    UserProfileResponse createUser(UserCreationRequest request, MultipartFile file);
    UserProfileResponse getUserById(Long id);
    List<UserDetailResponse> getAllUsers();
    UserProfileResponse updateUser(Long id, UserUpdateRequest request);
    void changeUserStatus(Long id, Status status);

}
