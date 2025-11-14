package com.khahnm04.ecommerce.dto.request.news;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    private String image;

    @NotBlank(message = "content cannot be blank")
    private String content;

    private String status;

}
