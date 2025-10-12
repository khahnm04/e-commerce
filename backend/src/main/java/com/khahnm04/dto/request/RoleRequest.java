package com.khahnm04.dto.request;

import lombok.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {

    private String name;

    private String description;

    private Set<String> permissions;

}
