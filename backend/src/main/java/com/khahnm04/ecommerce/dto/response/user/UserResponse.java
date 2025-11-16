package com.khahnm04.ecommerce.dto.response.user;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.response.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends BaseResponse<Long> {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private LocalDate dateOfBirth;
    private GenderEnum gender;
    private StatusEnum status;
    private LocalDateTime lastLoginAt;
    private Set<String> roles;

}
