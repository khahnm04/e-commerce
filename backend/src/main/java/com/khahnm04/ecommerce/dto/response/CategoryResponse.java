package com.khahnm04.ecommerce.dto.response;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse implements Serializable {

    private String id;
    private String name;
    private String description;
    private String image;
    private Long parentId;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;

}
