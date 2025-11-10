package com.khahnm04.ecommerce.service.address;

import com.khahnm04.ecommerce.dto.request.user.AddressUserRequest;
import com.khahnm04.ecommerce.dto.response.user.AddressUserResponse;

import java.util.List;

public interface AddressService {

    AddressUserResponse addAddress(AddressUserRequest request);
    List<AddressUserResponse> getAllAddresses();
    AddressUserResponse updateAddress(Long id, AddressUserRequest request);
    void deleteAddress(Long id);

}
