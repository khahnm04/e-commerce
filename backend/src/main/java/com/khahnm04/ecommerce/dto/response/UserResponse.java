package com.khahnm04.ecommerce.dto.response;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;
    private Set<RoleResponse> roles;

}
