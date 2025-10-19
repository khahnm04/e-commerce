package com.khahnm04.ecommerce.dto.response;

import com.khahnm04.ecommerce.constant.GenderEnum;
import com.khahnm04.ecommerce.constant.StatusEnum;
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
public class UserResponse implements Serializable {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private GenderEnum gender;
    private String image;
    private StatusEnum status;
    private Instant lastLoginAt;
    private Instant createdAt;
    private Long createdBy;
    private Instant updatedAt;
    private Long updatedBy;
    private Set<RoleResponse> roles;

}
