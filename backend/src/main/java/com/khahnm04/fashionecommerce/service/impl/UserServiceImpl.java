package com.khahnm04.fashionecommerce.service.impl;

import com.khahnm04.fashionecommerce.constant.GenderEnum;
import com.khahnm04.fashionecommerce.constant.RoleEnum;
import com.khahnm04.fashionecommerce.dto.request.UserCreationRequest;
import com.khahnm04.fashionecommerce.dto.request.UserUpdateRequest;
import com.khahnm04.fashionecommerce.dto.response.UserDetailResponse;
import com.khahnm04.fashionecommerce.dto.response.UserProfileResponse;
import com.khahnm04.fashionecommerce.entity.Role;
import com.khahnm04.fashionecommerce.entity.User;
import com.khahnm04.fashionecommerce.constant.StatusEnum;
import com.khahnm04.fashionecommerce.exception.AppException;
import com.khahnm04.fashionecommerce.exception.ErrorCode;
import com.khahnm04.fashionecommerce.mapper.UserMapper;
import com.khahnm04.fashionecommerce.repository.RoleRepository;
import com.khahnm04.fashionecommerce.repository.UserRepository;
import com.khahnm04.fashionecommerce.service.CloudinaryService;
import com.khahnm04.fashionecommerce.service.UserService;
import com.khahnm04.fashionecommerce.util.PhoneNumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
        user.setStatus(request.getStatus() == null ? StatusEnum.ACTIVE : request.getStatus());
        user.setGender(request.getGenderEnum() == null ? GenderEnum.OTHER : request.getGenderEnum());

        HashSet<String> roles = new HashSet<>();
        roles.add(RoleEnum.USER.name());
        //user.setRoles(roles);

        User userSaved = userRepository.save(user);
        return userMapper.toUserProfileResponse(userSaved);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDetailResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDetailResponse)
                .toList();
    }

    @Override
    @PostAuthorize("returnObject.phoneNumber == authentication.name")
    public UserProfileResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    public UserProfileResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String phoneNumber = context.getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    @Transactional
    public UserDetailResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        User userSaved = userRepository.save(user);
        return userMapper.toUserDetailResponse(userSaved);
    }

    @Override
    @Transactional
    public void changeUserStatus(Long id, StatusEnum status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(status);
        log.info("user status changed to {}", status);
    }

}
