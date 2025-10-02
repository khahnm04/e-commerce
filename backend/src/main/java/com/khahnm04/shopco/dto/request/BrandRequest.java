package com.khahnm04.shopco.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khahnm04.shopco.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Slug is required")
    private String slug;

    @JsonProperty("logo_url")
    private String logoUrl;

    private String website;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Short description is required")
    @JsonProperty("short_description")
    private String shortDescription;

    @NotBlank(message = "Description is required")
    private String description;

    private Status status;

}
