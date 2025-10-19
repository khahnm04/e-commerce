package com.khahnm04.ecommerce.dto.request;

import com.khahnm04.ecommerce.constant.GenderEnum;
import com.khahnm04.ecommerce.constant.StatusEnum;
import com.khahnm04.ecommerce.validation.email.ValidEmail;
import com.khahnm04.ecommerce.validation.password.ValidPassword;
import com.khahnm04.ecommerce.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserRequest implements Serializable {

    @Size(min = 3, max = 50, message = "USER_NAME_INVALID_LENGTH")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "USER_NAME_INVALID_FORMAT")
    private String username;

    @ValidEmail
    private String email;

    @ValidPhoneNumber
    private String phoneNumber;

    @ValidPassword
    private String password;

    @NotBlank(message = "FULL_NAME_REQUIRED")
    private String fullName;

    private LocalDate dateOfBirth;

    private GenderEnum genderEnum;

    private String image;

    private StatusEnum status;

    private Set<String> roles;

}
