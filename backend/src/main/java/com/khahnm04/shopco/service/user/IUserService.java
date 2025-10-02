package com.khahnm04.shopco.service.user;

import com.khahnm04.shopco.dto.request.UserCreationRequest;
import com.khahnm04.shopco.dto.response.UserCreationResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IUserService {

    UserCreationResponse createUser(UserCreationRequest request, MultipartFile file);
    List<UserCreationResponse> getUsers();
    UserCreationResponse getUserById(Integer id);


}
