package com.khahnm04.shopco.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    BRAND_EXISTED(1002, "Brand existed");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
