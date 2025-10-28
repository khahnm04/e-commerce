package com.khahnm04.ecommerce.dto.request;

import lombok.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest implements Serializable {

    private String name;

    private String description;

    private Set<String> permissions;

}
