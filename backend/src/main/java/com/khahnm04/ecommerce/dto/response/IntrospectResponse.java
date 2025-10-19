package com.khahnm04.ecommerce.dto.response;

import lombok.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectResponse implements Serializable {

    private boolean valid;

}

