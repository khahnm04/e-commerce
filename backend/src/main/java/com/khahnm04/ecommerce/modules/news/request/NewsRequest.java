package com.khahnm04.ecommerce.modules.news.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    @NotBlank(message = "title cannot be blank")
    private String title;

    private MultipartFile image;

    @NotBlank(message = "content cannot be blank")
    private String content;

    private String status;

}
