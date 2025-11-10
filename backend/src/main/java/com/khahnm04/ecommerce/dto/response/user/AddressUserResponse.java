package com.khahnm04.ecommerce.dto.response.user;

import com.khahnm04.ecommerce.common.enums.AddressTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUserResponse {

    private String province;
    private String ward;
    private String homeAddress;
    private String reminiscentName;
    private Boolean isDefault = false;
    private AddressTypeEnum addressType;
    private Long userId;

}
