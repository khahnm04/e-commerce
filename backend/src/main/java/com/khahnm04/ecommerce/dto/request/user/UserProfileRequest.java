package com.khahnm04.ecommerce.dto.request.user;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.common.validation.email.ValidEmail;
import com.khahnm04.ecommerce.common.validation.enums.ValidEnum;
import com.khahnm04.ecommerce.common.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserProfileRequest implements Serializable {

    @NotBlank(message = "fullName cannot be blank")
    private String fullName;

    @ValidEmail
    private String email;

    @ValidPhoneNumber
    @NotBlank(message = "phoneNumber cannot be blank")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @ValidEnum(name = "gender", enumClass = GenderEnum.class)
    private GenderEnum gender;

}
