package com.khahnm04.ecommerce.service.impl;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.dto.request.MyInfoRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.MyInfoResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.UserResponse;
import com.khahnm04.ecommerce.entity.QUser;
import com.khahnm04.ecommerce.entity.Role;
import com.khahnm04.ecommerce.entity.User;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.UserMapper;
import com.khahnm04.ecommerce.repository.RoleRepository;
import com.khahnm04.ecommerce.repository.UserRepository;
import com.khahnm04.ecommerce.service.CloudinaryService;
import com.khahnm04.ecommerce.service.contract.UserService;
import com.khahnm04.ecommerce.util.SortUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public UserResponse createUser(UserRequest request, MultipartFile file) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setImage(cloudinaryService.upload(file));

        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        User userSaved = userRepository.save(user);
        return userMapper.toUserResponse(userSaved);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @Override
    public PageResponse<UserResponse> getAllUsers(String search, int page, int size, List<String> sort) {
        Sort sortObj = SortUtils.parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        if (search != null && !search.trim().isEmpty()) {
            String term = search.trim().toLowerCase();
            builder.and(
                    user.username.lower().contains(term)
                            .or(user.email.lower().contains(term))
                            .or(user.phoneNumber.contains(term))
                            .or(user.fullName.lower().contains(term))
                            .or(user.gender.stringValue().lower().contains(term))
                            .or(user.status.stringValue().lower().contains(term))
                            .or(user.id.stringValue().contains(term))
                            .or(user.image.lower().contains(term))
                            .or(user.dateOfBirth.stringValue().contains(term))
            );
        }

        Page<User> userPage = userRepository.findAll(builder, pageable);
        Page<UserResponse> dtoPage = userPage.map(userMapper::toUserResponse);
        return PageResponse.fromPage(dtoPage);
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
    public void changeUserStatus(Long id, StatusEnum status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(status);
        log.info("user status changed to {}", status);
    }

    @Override
    public void softDeleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setDeletedAt(LocalDateTime.now());
        log.info("user soft deleted successfully");
    }

}
