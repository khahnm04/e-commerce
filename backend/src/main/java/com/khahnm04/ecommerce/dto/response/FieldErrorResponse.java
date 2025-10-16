package com.khahnm04.ecommerce.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldErrorResponse {

    private int code;
    private String field;
    private String message;

}
