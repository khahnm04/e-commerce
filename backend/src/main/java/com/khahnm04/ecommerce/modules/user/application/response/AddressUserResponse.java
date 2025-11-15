package com.khahnm04.ecommerce.modules.user.application.response;

import com.khahnm04.ecommerce.shared.common.enums.AddressTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUserResponse {

    private Long id;
    private String province;
    private String ward;
    private String homeAddress;
    private String reminiscentName;
    private Boolean isDefault = false;
    private AddressTypeEnum addressType;
    private Long userId;

}
