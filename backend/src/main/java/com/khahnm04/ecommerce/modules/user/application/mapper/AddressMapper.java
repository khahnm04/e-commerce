package com.khahnm04.ecommerce.modules.user.application.mapper;

import com.khahnm04.ecommerce.modules.user.application.request.AddressUserRequest;
import com.khahnm04.ecommerce.modules.user.application.response.AddressUserResponse;
import com.khahnm04.ecommerce.modules.user.domain.entity.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(AddressUserRequest request);

    @Mapping(source = "user.id", target = "userId")
    AddressUserResponse toAddressResponse(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddress(@MappingTarget Address address, AddressUserRequest request);

}
