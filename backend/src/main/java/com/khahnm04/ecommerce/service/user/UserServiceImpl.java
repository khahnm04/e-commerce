package com.khahnm04.ecommerce.service.user;

import com.khahnm04.ecommerce.dto.request.user.AddressUserRequest;
import com.khahnm04.ecommerce.dto.request.user.ChangePasswordRequest;
import com.khahnm04.ecommerce.dto.request.user.ProfileRequest;
import com.khahnm04.ecommerce.dto.request.user.UserRequest;
import com.khahnm04.ecommerce.dto.response.user.AddressUserResponse;
import com.khahnm04.ecommerce.dto.response.user.ProfileResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.user.UserResponse;
import com.khahnm04.ecommerce.entity.user.Address;
import com.khahnm04.ecommerce.entity.auth.Role;
import com.khahnm04.ecommerce.entity.user.User;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.AddressMapper;
import com.khahnm04.ecommerce.mapper.UserMapper;
import com.khahnm04.ecommerce.repository.AddressRepository;
import com.khahnm04.ecommerce.repository.RoleRepository;
import com.khahnm04.ecommerce.repository.UserRepository;
import com.khahnm04.ecommerce.service.upload.CloudinaryService;
import com.khahnm04.ecommerce.util.SecurityUtils;
import com.khahnm04.ecommerce.util.SortUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public UserResponse createUser(UserRequest request, MultipartFile file) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        if (StringUtils.hasText(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.fromUserRequestToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAvatar(cloudinaryService.upload(file));
        assignRoleToUser(user, request.getRoles());

        User savedUser = userRepository.save(user);
        log.info("User created with Id = {}", savedUser.getId());
        return buildUserResponse(savedUser);
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<User> userPage = userRepository.findAllByDeletedAtIsNull(pageable);
        Page<UserResponse> dtoPage = userPage.map(this::buildUserResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<UserResponse> getAllDeletedUsers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<User> userPage = userRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<UserResponse> dtoPage = userPage.map(this::buildUserResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public UserResponse getUserDetailById(Long id) {
        return buildUserResponse(getUserById(id));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request, MultipartFile file) {
        User user = getUserById(id);

        if (!Objects.equals(user.getPhoneNumber(), request.getPhoneNumber())
                && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        if (StringUtils.hasText(request.getEmail())
                && !Objects.equals(user.getEmail(), request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        assignRoleToUser(user, request.getRoles());

        if (file != null && !file.isEmpty()) {
            user.setAvatar(cloudinaryService.upload(file));
        }

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
    public ProfileResponse getProfile() {
        User user = getCurrentUser();
        return userMapper.fromUserToProfileResponse(user);
    }

    @Override
    public ProfileResponse updateProfile(ProfileRequest request) {
        User user = getCurrentUser();

        if (!Objects.equals(user.getEmail(), request.getEmail())
                && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        if (StringUtils.hasText(request.getEmail())
                && !Objects.equals(user.getEmail(), request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        userMapper.updateProfile(user, request);

        User savedUser = userRepository.save(user);
        log.info("Updated profile user with id {}", savedUser.getId());
        return userMapper.fromUserToProfileResponse(savedUser);
    }

    @Override
    public void uploadAvatar(MultipartFile file) {
        User user = getCurrentUser();
        user.setAvatar(cloudinaryService.upload(file));
        userRepository.save(user);
        log.info("Updated avatar user with Id = {}", user.getId());
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = getCurrentUser();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRMATION_MISMATCH);
        }
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.NEW_PASSWORD_SAME_AS_OLD);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("Change password user with id {}", user.getId());
    }

    @Override
    public AddressUserResponse addAddress(AddressUserRequest request) {
        Address address = addressMapper.toAddress(request);

        address.setUser(getCurrentUser());
        Address savedAddress = addressRepository.save(address);

        log.info("Saved new address with id {}", savedAddress.getId());
        return addressMapper.toAddressResponse(savedAddress);
    }

    @Override
    public List<AddressUserResponse> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toAddressResponse)
                .toList();
    }

    @Override
    public AddressUserResponse updateAddress(Long id, AddressUserRequest request) {
        Address address = getAddressById(id);

        addressMapper.updateAddress(address, request);
        Address savedAddress = addressRepository.save(address);

        log.info("Address updated with id {}", address.getId());
        return addressMapper.toAddressResponse(savedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.delete(getAddressById(id));
        log.info("Address deleted with id {}", id);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
    }

    private void assignRoleToUser(User user, Set<Long> roleIds) {
        Set<Role> roles = Optional.ofNullable(roleIds)
                .orElseGet(Collections::emptySet)
                .stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)))
                .collect(Collectors.toSet());
        user.setRoles(roles);
    }

    private User getCurrentUser() {
        return userRepository.findByIdentifier(SecurityUtils.extractPrincipal())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private UserResponse buildUserResponse(User user) {
        UserResponse userResponse = userMapper.fromUserToUserResponse(user);
        Set<String> roleNames = user.getRoles() != null
                ? user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
                : Set.of();
        userResponse.setRoles(roleNames);
        return userResponse;
    }

}
