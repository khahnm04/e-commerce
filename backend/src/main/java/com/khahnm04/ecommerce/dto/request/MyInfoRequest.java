package com.khahnm04.ecommerce.dto.request;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
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
public class MyInfoRequest implements Serializable {

    @NotBlank(message = "FULL_NAME_REQUIRED")
    private String fullName;

    @Size(min = 3, max = 50, message = "USER_NAME_INVALID_LENGTH")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "USER_NAME_INVALID_FORMAT")
    private String username;

    private GenderEnum gender;

    private LocalDate dateOfBirth;

}
