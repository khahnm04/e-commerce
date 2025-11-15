package com.khahnm04.ecommerce.modules.user.application.request;

import com.khahnm04.ecommerce.shared.common.enums.AddressTypeEnum;
import com.khahnm04.ecommerce.shared.common.validation.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUserRequest {

    @NotBlank(message = "province cannot be blank")
    private String province;

    @NotBlank(message = "ward cannot be blank")
    private String ward;

    @NotBlank(message = "home address cannot be blank")
    private String homeAddress;

    private String reminiscentName;

    private Boolean isDefault = false;

    @ValidEnum(name = "addressType", enumClass = AddressTypeEnum.class)
    private AddressTypeEnum addressType;

}
