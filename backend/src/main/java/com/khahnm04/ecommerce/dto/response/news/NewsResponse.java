package com.khahnm04.ecommerce.dto.response.news;

import com.khahnm04.ecommerce.dto.response.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse extends BaseResponse<Long> {

    private String slug;
    private String title;
    private String image;
    private String content;
    private String status;

}
