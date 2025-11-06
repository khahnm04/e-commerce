package com.khahnm04.ecommerce.service.user;

import com.khahnm04.ecommerce.dto.request.ProfileRequest;
import com.khahnm04.ecommerce.dto.request.UserRequest;
import com.khahnm04.ecommerce.dto.response.ProfileResponse;
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
import com.khahnm04.ecommerce.util.SecurityUtils;
import com.khahnm04.ecommerce.util.SortUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;

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
        user.setAvatar(cloudinaryService.upload(file));

        List<Role> roles = Optional.ofNullable(request.getRoles())
                .filter(list -> !list.isEmpty())
                .map(roleRepository::findAllById)
                .orElse(Collections.emptyList());
        user.setRoles(new HashSet<>(roles));

        User savedUser = saveUser(user);
        log.info("User with id {} has been saved", savedUser.getId());
        return userMapper.toUserResponse(savedUser);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size, String sort, String... search) {
        Sort sortObj = SortUtils.parseSort(sort);
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size, sortObj);

        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        if (search != null && search.length > 0) {
            BooleanBuilder searchBuilder = new BooleanBuilder();
            Arrays.stream(search)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(String::toLowerCase)
                    .map(s -> s.matches("\\d{4}-\\d{2}-\\d{2} .*") && s.contains("T") ? s.replace("T", " ") : s)
                    .forEach(keyword -> searchBuilder.or(
                            user.username.lower().contains(keyword)
                            .or(user.email.lower().contains(keyword))
                            .or(user.phoneNumber.contains(keyword))
                            .or(user.fullName.lower().contains(keyword))
                            .or(user.dateOfBirth.stringValue().contains(keyword))
                            .or(user.gender.stringValue().lower().contains(keyword))
                            .or(user.status.stringValue().lower().contains(keyword))
                            .or(user.createdAt.stringValue().contains(keyword))
                            .or(user.createdBy.stringValue().contains(keyword))
                            .or(user.updatedAt.stringValue().contains(keyword))
                            .or(user.updatedBy.stringValue().contains(keyword))
                            .or(user.deletedAt.stringValue().contains(keyword))
                    ));
            builder.and(searchBuilder);
        }

        Page<User> userPage = userRepository.findAll(builder, pageable);
        Page<UserResponse> dtoPage = userPage.map(userMapper::toUserResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    @PostAuthorize("returnObject.phoneNumber == authentication.name")
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getProfile() {
        User user = userRepository.findByIdentifier(SecurityUtils.extractPrincipal())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public ProfileResponse updateProfile(ProfileRequest request) {
        User user = userRepository.findByIdentifier(SecurityUtils.extractPrincipal())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateProfile(user, request);
        User savedUser = saveUser(user);
        log.info("Updated profile user with id {}", savedUser.getId());
        return userMapper.toProfileResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        User savedUser = saveUser(user);
        log.info("Updated user with id {}", savedUser.getId());
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public void updateUserStatus(Long id, StatusEnum status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(status);
        saveUser(user);
        log.info("user status changed to {}", status);
    }

    @Override
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setDeletedAt(LocalDateTime.now());
        saveUser(user);
        log.info("user soft deleted successfully");
    }

    private User saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        return user;
    }

}
