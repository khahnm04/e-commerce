package com.khahnm04.ecommerce.modules.user.application.response;

import com.khahnm04.ecommerce.shared.common.enums.GenderEnum;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserProfileResponse implements Serializable {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private LocalDate dateOfBirth;
    private GenderEnum gender;

}
