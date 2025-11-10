package com.khahnm04.ecommerce.dto.request.user;

import com.khahnm04.ecommerce.validation.password.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @ValidPassword
    @NotBlank(message = "oldPassword cannot be blank")
    String oldPassword;

    @ValidPassword
    @NotBlank(message = "newPassword cannot be blank")
    String newPassword;

    @NotBlank(message = "confirmPassword cannot be blank")
    String confirmPassword;

}
