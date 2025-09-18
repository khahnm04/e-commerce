package com.khahnm04.shopco.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    BRAND_EXISTED(1002, "Brand existed"),
    BRAND_NOT_EXIST(1002, "Brand not exist");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
