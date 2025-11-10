package com.khahnm04.ecommerce.dto.response.brand;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String country;
    private String logo;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;

}
