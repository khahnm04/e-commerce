package com.khahnm04.shopco.service.user;

import com.khahnm04.shopco.enums.ErrorCode;
import com.khahnm04.shopco.enums.Status;
import com.khahnm04.shopco.exception.AppException;
import com.khahnm04.shopco.mapper.UserMapper;
import com.khahnm04.shopco.dto.request.UserCreationRequest;
import com.khahnm04.shopco.dto.response.UserCreationResponse;
import com.khahnm04.shopco.entity.User;
import com.khahnm04.shopco.repository.UserRepository;
import com.khahnm04.shopco.service.storage.IFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IFileUploadService cloudinaryService;

    @Override
    public UserCreationResponse createUser(UserCreationRequest request, MultipartFile file) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        if (request.getUsername() != null && userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_NAME_EXISTED);
        }
        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (request.getStatus() == null) {
            request.setStatus(Status.ACTIVE);
        }
        request.setAvatarUrl(cloudinaryService.uploadFileIfPresent(file, "shopco-files/users"));
        User newUser = userMapper.toUser(request);
        return userMapper.toUserCreationResponse(userRepository.save(newUser));
    }

    @Override
    public List<UserCreationResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserCreationResponse)
                .toList();
    }

    @Override
    public UserCreationResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        return userMapper.toUserCreationResponse(user);
    }

}
