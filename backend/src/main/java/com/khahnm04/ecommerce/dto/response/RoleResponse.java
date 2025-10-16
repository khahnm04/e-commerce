package com.khahnm04.ecommerce.dto.response;

import lombok.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private String name;

    private String description;

    private Set<PermissionResponse> permissions;

}
