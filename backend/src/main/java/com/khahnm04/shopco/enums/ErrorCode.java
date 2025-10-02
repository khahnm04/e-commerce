package com.khahnm04.shopco.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_EXIST(1001, "User not exist"),
    PHONE_NUMBER_EXISTED(1003, "Phone number existed"),
    USER_NAME_EXISTED(1002, "User name existed"),
    EMAIL_EXISTED(1004, "Email existed"),

    BRAND_EXISTED(2001, "Brand existed"),
    BRAND_NOT_EXIST(2002, "Brand not exist");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
