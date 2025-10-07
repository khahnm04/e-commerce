package com.khahnm04.dto.response;

import com.khahnm04.enums.Gender;
import com.khahnm04.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserDetailResponse implements Serializable {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String image;
    private Status status;
    private Instant lastLoginAt;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<String> roles;

}
