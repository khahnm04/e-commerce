package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.AddressUserRequest;
import com.khahnm04.ecommerce.dto.response.AddressUserResponse;
import com.khahnm04.ecommerce.entity.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(AddressUserRequest request);

    AddressUserResponse toAddressResponse(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddress(@MappingTarget Address address, AddressUserRequest request);

}
