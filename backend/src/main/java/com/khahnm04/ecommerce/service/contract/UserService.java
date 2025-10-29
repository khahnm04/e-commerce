package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.request.MyInfoRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.MyInfoResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request, MultipartFile file);
    PageResponse<UserResponse> getAllUsers(String search, int page, int size, String sort);
    UserResponse getUserById(Long id);
    UserResponse getMyInfo();
    MyInfoResponse updateMyInfo(MyInfoRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    void changeUserStatus(Long id, StatusEnum status);
    void softDeleteUserById(Long id);

}
