package com.khahnm04.ecommerce.dto.request.banner;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.common.validation.enums.ValidEnum;
import com.khahnm04.ecommerce.common.validation.upload.NotEmptyFile;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequest implements Serializable {

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotEmptyFile(message = "image cannot be blank")
    private MultipartFile image;
    
    @ValidEnum(name = "status", enumClass = StatusEnum.class)
    private String status;
    
}
