package com.khahnm04.ecommerce.dto.request;

import com.khahnm04.ecommerce.validation.email.ValidEmail;
import com.khahnm04.ecommerce.validation.phone.ValidPhoneNumber;
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

    @NotBlank(message = "PASSWORD_REQUIRED")
    private String password;

}
