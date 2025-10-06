package com.khahnm04.service.user;

import com.khahnm04.dto.request.UserCreationRequest;
import com.khahnm04.dto.request.UserUpdateRequest;
import com.khahnm04.dto.response.UserDetailResponse;
import com.khahnm04.dto.response.UserProfileResponse;
import com.khahnm04.entity.User;
import com.khahnm04.enums.Gender;
import com.khahnm04.enums.Status;
import com.khahnm04.exception.AppException;
import com.khahnm04.exception.ErrorCode;
import com.khahnm04.mapper.UserMapper;
import com.khahnm04.repository.UserRepository;
import com.khahnm04.service.upload.IFileUploadService;
import com.khahnm04.util.PhoneNumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IFileUploadService cloudinaryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserProfileResponse createUser(UserCreationRequest request, MultipartFile file) {
        String normalizePhoneNumber = "";
        if (request.getPhoneNumber() != null) {
            normalizePhoneNumber = PhoneNumberUtil.normalizePhoneNumber(request.getPhoneNumber());
        }
        if (userRepository.existsByPhoneNumber(normalizePhoneNumber)) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_NAME_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(normalizePhoneNumber);
        user.setImage(cloudinaryService.uploadFileIfPresent(file));
        user.setStatus(request.getStatus() == null ? Status.ACTIVE : request.getStatus());
        user.setGender(request.getGender() == null ? Gender.OTHER : request.getGender());

        User userSaved = userRepository.save(user);
        return userMapper.toUserProfileResponse(userSaved);
    }

    @Override
    public UserProfileResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    public List<UserDetailResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDetailResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserProfileResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        User userSaved = userRepository.save(user);
        return userMapper.toUserProfileResponse(userSaved);
    }

    @Override
    @Transactional
    public void changeUserStatus(Long id, Status status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(status);
        log.info("user status changed to {}", status);
    }

}
