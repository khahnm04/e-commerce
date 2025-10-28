package com.khahnm04.ecommerce.dto.response;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse implements Serializable {

    private Long userId;

}
