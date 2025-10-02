package com.khahnm04.shopco.dto.response;

import com.khahnm04.shopco.enums.Gender;
import com.khahnm04.shopco.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserCreationResponse {

    private Integer id;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String avatarUrl;
    private Status status;

}
