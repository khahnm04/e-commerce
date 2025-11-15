package com.khahnm04.ecommerce.modules.banner.request;

import com.khahnm04.ecommerce.shared.common.enums.StatusEnum;
import com.khahnm04.ecommerce.shared.common.validation.enums.ValidEnum;
import com.khahnm04.ecommerce.shared.common.validation.upload.NotEmptyFile;
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
