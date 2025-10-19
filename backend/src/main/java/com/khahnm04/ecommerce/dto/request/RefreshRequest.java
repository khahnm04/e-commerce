package com.khahnm04.ecommerce.dto.request;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest implements Serializable {

    private String token;

}
