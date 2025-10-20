package com.khahnm04.ecommerce.dto.response;

import com.khahnm04.ecommerce.constant.StatusEnum;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private String name;
    private String description;
    private String image;
    private Long parent;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;

}
