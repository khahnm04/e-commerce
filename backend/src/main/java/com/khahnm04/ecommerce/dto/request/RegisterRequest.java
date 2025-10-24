package com.khahnm04.ecommerce.dto.request;

import com.khahnm04.ecommerce.validation.email.ValidEmail;
import com.khahnm04.ecommerce.validation.password.ValidPassword;
import com.khahnm04.ecommerce.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {

    @NotBlank(message = "FULL_NAME_REQUIRED")
    private String fullName;

    @ValidEmail
    private String email;

    @ValidPhoneNumber
    private String phoneNumber;

    @ValidPassword
    @NotBlank(message = "PASSWORD_REQUIRED")
    private String password;

}
