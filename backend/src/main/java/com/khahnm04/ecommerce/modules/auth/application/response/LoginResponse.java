package com.khahnm04.ecommerce.modules.auth.application.response;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private Long userId;

}
