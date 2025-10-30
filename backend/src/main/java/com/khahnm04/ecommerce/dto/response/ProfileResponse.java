package com.khahnm04.ecommerce.dto.response;

import com.khahnm04.ecommerce.common.enums.GenderEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProfileResponse implements Serializable {

    private String fullName;
    private String username;
    private GenderEnum gender;
    private LocalDate dateOfBirth;

}
