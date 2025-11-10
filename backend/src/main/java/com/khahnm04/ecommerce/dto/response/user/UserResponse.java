package com.khahnm04.ecommerce.dto.response.user;

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
    private String fullName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private LocalDate dateOfBirth;
    private GenderEnum gender;
    private StatusEnum status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;
    private Set<String> roles;

}
