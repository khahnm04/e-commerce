package com.khahnm04.ecommerce.dto.response.user;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProfileResponse implements Serializable {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private LocalDate dateOfBirth;
    private GenderEnum gender;

}
