package com.khahnm04.ecommerce.dto.response;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private UserProfileResponse user;

}
