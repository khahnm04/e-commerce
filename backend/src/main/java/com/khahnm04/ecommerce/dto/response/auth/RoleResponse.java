package com.khahnm04.ecommerce.dto.response.auth;

import lombok.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;

}
