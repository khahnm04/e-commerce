package com.khahnm04.ecommerce.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class FieldErrorResponse implements Serializable {

    private int code;
    private String field;
    private String message;

}
