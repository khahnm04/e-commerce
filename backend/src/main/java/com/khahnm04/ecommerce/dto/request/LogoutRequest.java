package com.khahnm04.ecommerce.dto.request;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest implements Serializable {

    private String refreshToken;

}
