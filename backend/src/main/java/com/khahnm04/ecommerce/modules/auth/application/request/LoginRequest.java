package com.khahnm04.ecommerce.modules.auth.application.request;

import com.khahnm04.ecommerce.shared.common.validation.email.ValidEmail;
import com.khahnm04.ecommerce.shared.common.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    @ValidPhoneNumber
    private String phoneNumber;

    @ValidEmail
    private String email;

    @NotBlank(message = "password cannot be blank")
    private String password;

}
