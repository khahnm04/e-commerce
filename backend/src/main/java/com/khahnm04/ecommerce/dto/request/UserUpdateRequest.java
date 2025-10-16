package com.khahnm04.ecommerce.dto.request;

import com.khahnm04.ecommerce.constant.GenderEnum;
import com.khahnm04.ecommerce.constant.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class UserUpdateRequest implements Serializable {

    private String username;

    private String email;

    private String phoneNumber;

    private String password;

    private String fullName;

    private LocalDate dateOfBirth;

    private GenderEnum gender;

    private String image;

    private StatusEnum status;

    private List<String> roles;

}
