package com.khahnm04.ecommerce.dto.request.user;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.common.validation.email.ValidEmail;
import com.khahnm04.ecommerce.common.validation.enums.ValidEnum;
import com.khahnm04.ecommerce.common.validation.password.ValidPassword;
import com.khahnm04.ecommerce.common.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserRequest implements Serializable {

    @NotBlank(message = "fullName is required")
    private String fullName;

    @ValidEmail
    private String email;

    @ValidPhoneNumber
    @NotBlank(message = "phoneNumber cannot be blank")
    private String phoneNumber;

    @ValidPassword
    @NotBlank(message = "password cannot be blank")
    private String password;

    private MultipartFile avatar;

    private LocalDate dateOfBirth;

    @ValidEnum(name = "gender", enumClass = GenderEnum.class)
    private String gender;

    @ValidEnum(name = "status", enumClass = StatusEnum.class)
    private String status;

    private Set<Long> roles;

}
