package com.khahnm04.shopco.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khahnm04.shopco.enums.Gender;
import com.khahnm04.shopco.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserCreationRequest {

    @NotBlank(message = "fullName cannot be blank")
    private String fullName;

    private String username;

    @Email
    private String email;

    @NotBlank(message = "phoneNumber cannot be blank")
    @Pattern(
        regexp = "^(0|\\+84)(3[2-9]|5[689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$",
        message = "Invalid Vietnam phone number format"
    )
    private String phoneNumber;

    @NotNull(message = "dateOfBirth cannot be blank")
    private LocalDate dateOfBirth;

    @NotNull(message = "gender cannot be blank")
    private Gender gender;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @NotBlank(message = "password cannot be blank")
    private String password;

    private Status status;

}
