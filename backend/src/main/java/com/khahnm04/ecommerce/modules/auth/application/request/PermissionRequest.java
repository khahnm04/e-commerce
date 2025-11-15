package com.khahnm04.ecommerce.modules.auth.application.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest implements Serializable {

    @NotBlank(message = "name cannot be blank")
    private String name;

    private String description;

}
