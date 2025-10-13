package com.khahnm04.fashionecommerce.dto.response;

import com.khahnm04.fashionecommerce.constant.GenderEnum;
import com.khahnm04.fashionecommerce.constant.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserProfileResponse implements Serializable {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private GenderEnum gender;
    private String image;
    private StatusEnum status;

}
