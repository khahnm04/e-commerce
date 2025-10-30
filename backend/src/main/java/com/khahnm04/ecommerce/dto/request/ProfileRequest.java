package com.khahnm04.ecommerce.dto.request;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
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

@Getter
@Setter
@Builder
public class ProfileRequest implements Serializable {

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters,")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers and underscore")
    private String username;

    @ValidEmail
    private String email;

    @ValidPhoneNumber
    private String phoneNumber;

    @NotBlank(message = "fullName is required")
    private String fullName;

    private LocalDate dateOfBirth;

    private GenderEnum gender;

    private String image;

}
