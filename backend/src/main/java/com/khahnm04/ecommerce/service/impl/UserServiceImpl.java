package com.khahnm04.ecommerce.service.impl;

import com.khahnm04.ecommerce.constant.GenderEnum;
import com.khahnm04.ecommerce.dto.request.MyInfoRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.MyInfoResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.entity.Role;
import com.khahnm04.ecommerce.entity.User;
import com.khahnm04.ecommerce.constant.StatusEnum;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.UserMapper;
import com.khahnm04.ecommerce.repository.RoleRepository;
import com.khahnm04.ecommerce.repository.UserRepository;
import com.khahnm04.ecommerce.service.CloudinaryService;
import com.khahnm04.ecommerce.service.contract.UserService;
import com.khahnm04.ecommerce.util.PhoneNumberUtils;
import com.khahnm04.ecommerce.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
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
    public UserResponse createUser(UserRequest request, MultipartFile file) {
        String normalizePhoneNumber = "";
        if (request.getPhoneNumber() != null) {
            normalizePhoneNumber = PhoneNumberUtils.normalizePhoneNumber(request.getPhoneNumber());
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

        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        User userSaved = userRepository.save(user);
        return userMapper.toUserResponse(userSaved);
    }

    @Override
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    @PostAuthorize("returnObject.phoneNumber == authentication.name")
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String phoneNumber = context.getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public MyInfoResponse updateMyInfo(MyInfoRequest request) {
        var context = SecurityContextHolder.getContext();
        String phoneNumber = context.getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());

        return userMapper.toMyInfoResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        User userSaved = userRepository.save(user);
        return userMapper.toUserResponse(userSaved);
    }

    @Override
    @Transactional
    public void changeUserStatus(Long id, StatusEnum status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(status);
        log.info("user status changed to {}", status);
    }

    @Override
    @Transactional
    public void softDeleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setDeletedAt(LocalDateTime.now());
        log.info("user soft deleted successfully");
    }

}
