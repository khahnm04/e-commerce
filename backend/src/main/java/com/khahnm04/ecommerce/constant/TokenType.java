package com.khahnm04.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {

    ACCESS("access_token"),
    REFRESH("refresh_token");

    private final String type;

}
