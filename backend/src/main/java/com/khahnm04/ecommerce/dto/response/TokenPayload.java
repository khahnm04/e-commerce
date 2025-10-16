package com.khahnm04.ecommerce.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder
public class TokenPayload {

    private String jwtId;
    private String token;
    private Date expiredTime;

}
