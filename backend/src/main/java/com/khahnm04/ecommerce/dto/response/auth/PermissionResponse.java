package com.khahnm04.ecommerce.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse implements Serializable {

    private Long id;
    private String name;
    private String description;

}
