package com.khahnm04.ecommerce.service.user;

import com.khahnm04.ecommerce.dto.request.user.ChangePasswordRequest;
import com.khahnm04.ecommerce.dto.request.user.ProfileRequest;
import com.khahnm04.ecommerce.dto.request.user.UserRequest;
import com.khahnm04.ecommerce.dto.response.FieldErrorResponse;
import com.khahnm04.ecommerce.dto.response.user.ProfileResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.user.UserResponse;
import com.khahnm04.ecommerce.entity.Role;
import com.khahnm04.ecommerce.entity.User;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.BusinessValidationException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.UserMapper;
import com.khahnm04.ecommerce.repository.RoleRepository;
import com.khahnm04.ecommerce.repository.UserRepository;
import com.khahnm04.ecommerce.service.CloudinaryService;
import com.khahnm04.ecommerce.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        validateRequest(request, null);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAvatar(cloudinaryService.upload(file));
        assignRoleToUser(user, request.getRoles());
        User savedUser = userRepository.save(user);
        log.info("User created with Id = {}", savedUser.getId());
        return buildUserResponse(savedUser);
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> userPage = userRepository.findAllByDeletedAtIsNull(pageable);
        Page<UserResponse> dtoPage = userPage.map(this::buildUserResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<UserResponse> getAllDeletedUsers(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> userPage = userRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<UserResponse> dtoPage = userPage.map(this::buildUserResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public UserResponse getUserDetailById(Long id) {
        User user = getUserById(id);
        return buildUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request, MultipartFile file) {
        User user = getUserById(id);
        validateRequest(request, user);
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (file != null && !file.isEmpty()) {
            user.setAvatar(cloudinaryService.upload(file));
        }
        assignRoleToUser(user, request.getRoles());
        User savedUser = userRepository.save(user);
        log.info("User updated with Id = {}", id);
        return buildUserResponse(savedUser);
    }

    @Override
    public void updateUserStatus(Long id, StatusEnum status) {
        User user = getUserById(id);
        user.setStatus(status);
        userRepository.save(user);
        log.info("User status updated with Id = {}", id);
    }

    @Override
    public void updateUserRole(Long id, Set<Long> roles) {
        User user = getUserById(id);
        assignRoleToUser(user, roles);
        userRepository.save(user);
        log.info("User role updated with Id = {}", id);
    }

    @Override
    public void softDeleteUser(Long id) {
        User user = getUserById(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        log.info("User soft deleted with id {}", id);
    }

    @Override
    public void restoreUser(Long id) {
        User user = getUserById(id);
        user.setDeletedAt(null);
        userRepository.save(user);
        log.info("User restored with id {}", id);
    }

    @Override
    public UserResponse getProfile() {
        User user = getCurrentUser();
        return userMapper.toUserResponse(user);
    }

    @Override
    public ProfileResponse updateProfile(ProfileRequest request) {
        User user = getCurrentUser();
        userMapper.updateProfile(user, request);
        User savedUser = userRepository.save(user);
        log.info("Updated profile user with id {}", savedUser.getId());
        return userMapper.toProfileResponse(savedUser);
    }

    @Override
    public void uploadAvatar(MultipartFile file) {
        User user = getCurrentUser();
        user.setAvatar(cloudinaryService.upload(file));
        User savedUser = userRepository.save(user);
        log.info("Updated avatar user with id {}", savedUser.getId());
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = getCurrentUser();
        if (!Objects.equals(user.getPassword(), request.getOldPassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRMATION_MISMATCH);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("Change password user with id {}", savedUser.getId());
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateRequest(UserRequest request, User existingUser) {
        List<FieldErrorResponse> errors = new ArrayList<>();
        if (existingUser == null) {
            if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
                errors.add(FieldErrorResponse.builder()
                        .field("phoneNumber")
                        .message("phoneNumber already exists")
                        .build());
            }
            if (StringUtils.hasText(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                errors.add(FieldErrorResponse.builder()
                        .field("email")
                        .message("Email already exists")
                        .build());
            }
        } else {
            if (!Objects.equals(existingUser.getPhoneNumber(), request.getPhoneNumber())
                    && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
                errors.add(FieldErrorResponse.builder()
                        .field("phoneNumber")
                        .message("phoneNumber already exists")
                        .build());
            }
            if (StringUtils.hasText(request.getEmail())
                    && !Objects.equals(existingUser.getEmail(), request.getEmail())
                    && userRepository.existsByEmail(request.getEmail())) {
                errors.add(FieldErrorResponse.builder()
                        .field("email")
                        .message("Email already exists")
                        .build());
            }
        }
        if (!errors.isEmpty()) {
            throw new BusinessValidationException(errors);
        }
    }

    private void assignRoleToUser(User user, Set<Long> roleIds) {
        List<Role> roles = Optional.ofNullable(roleIds)
                .filter(list -> !list.isEmpty())
                .map(roleRepository::findAllById)
                .orElse(Collections.emptyList());
        user.setRoles(new HashSet<>(roles));
    }

    private User getCurrentUser() {
        return userRepository.findByIdentifier(SecurityUtils.extractPrincipal())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private UserResponse buildUserResponse(User user) {
        UserResponse userResponse = userMapper.toUserResponse(user);
        Set<String> roleNames = user.getRoles() != null
                ? user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
                : Set.of();
        userResponse.setRoles(roleNames);
        return userResponse;
    }

}
