package com.khahnm04.ecommerce.dto.request;

import com.khahnm04.ecommerce.common.enums.AddressTypeEnum;
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

    private AddressTypeEnum addressType;

    private Long userId;

}
