package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.request.AddressUserRequest;
import com.khahnm04.ecommerce.dto.response.AddressUserResponse;

import java.util.List;

public interface AddressService {

    AddressUserResponse addAddress(AddressUserRequest request);
    List<AddressUserResponse> getAllAddresses();
    AddressUserResponse updateAddress(Long id, AddressUserRequest request);
    void deleteAddress(Long id);

}
