package com.khahnm04.dto.request;

import com.khahnm04.enums.Gender;
import com.khahnm04.enums.Status;
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
public class UserCreationRequest implements Serializable {

    @Size(min = 3, max = 50, message = "USERNAME_INVALID_LENGTH")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "USERNAME_INVALID_FORMAT")
    private String username;

    private String email;

    private String phoneNumber;

    private String password;

    private String fullName;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String image;

    private Status status;

}
