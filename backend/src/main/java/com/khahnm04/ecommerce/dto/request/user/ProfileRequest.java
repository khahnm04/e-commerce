package com.khahnm04.ecommerce.dto.request.user;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.validation.email.ValidEmail;
import com.khahnm04.ecommerce.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProfileRequest implements Serializable {

    @NotBlank(message = "fullName cannot be blank")
    private String fullName;

    @ValidEmail
    private String email;

    @ValidPhoneNumber
    private String phoneNumber;

    private GenderEnum gender;

    private LocalDate dateOfBirth;

}
