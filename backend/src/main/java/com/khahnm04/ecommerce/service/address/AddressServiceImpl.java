package com.khahnm04.ecommerce.service.address;

import com.khahnm04.ecommerce.dto.request.user.AddressUserRequest;
import com.khahnm04.ecommerce.dto.response.user.AddressUserResponse;
import com.khahnm04.ecommerce.entity.Address;
import com.khahnm04.ecommerce.entity.User;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.AddressMapper;
import com.khahnm04.ecommerce.repository.AddressRepository;
import com.khahnm04.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Override
    public AddressUserResponse addAddress(AddressUserRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Address address = addressMapper.toAddress(request);
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        log.info("Saved new address with id {}", savedAddress.getId());

        AddressUserResponse response = addressMapper.toAddressResponse(savedAddress);
        response.setUserId(savedAddress.getUser().getId());
        return response;
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
        log.info("Updated address with id {}", address.getId());

        AddressUserResponse response = addressMapper.toAddressResponse(savedAddress);
        response.setUserId(savedAddress.getUser().getId());
        return response;
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = getAddressById(id);
        addressRepository.delete(address);
        log.info("Deleted address with id {}", address.getId());
    }

    private Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
    }

}
