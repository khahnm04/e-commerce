package com.khahnm04.shopco.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {

    private String name;

    private String slug;

    @JsonProperty("logo_url")
    private String logoUrl;

    private String website;

    private String country;

    @JsonProperty("short_description")
    private String shortDescription;

    private String description;

}
