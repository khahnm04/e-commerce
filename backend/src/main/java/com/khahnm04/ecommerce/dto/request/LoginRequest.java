package com.khahnm04.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    @NotBlank(message = "EMAIL_OR_PHONE_NUMBER_REQUIRED")
    private String identifier;

    @NotBlank(message = "PASSWORD_REQUIRED")
    private String password;

}
