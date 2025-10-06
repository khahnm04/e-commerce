package com.khahnm04.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(1002, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    // ========== 2xxx: USER ==========
    // user
    USER_NOT_FOUND(2000, "user not found", HttpStatus.NOT_FOUND),
    USER_NAME_EXISTED(2001, "username existed", HttpStatus.CONFLICT),
    USER_NAME_INVALID_LENGTH(2002, "Username must be between {min} and {max} characters", HttpStatus.BAD_REQUEST),
    USER_NAME_INVALID_FORMAT(2003, "Username can only contain letters, numbers and underscore", HttpStatus.BAD_REQUEST),
    // email
    EMAIL_EXISTED(2100, "email existed", HttpStatus.CONFLICT),
    EMAIL_REQUIRED(2101, "ValidEmail cannot be blank", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID_FORMAT(2102, "ValidEmail format is invalid", HttpStatus.BAD_REQUEST),
    // phone number
    PHONE_NUMBER_EXISTED(2200, "phone number existed", HttpStatus.CONFLICT),
    PHONE_NUMBER_REQUIRED(2201, "ValidPhoneNumber number cannot be blank", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID_FORMAT(2202, "Phone number format is invalid", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID_LENGTH(2203, "Phone number must be exactly 10 digits", HttpStatus.BAD_REQUEST),
    // password
    PASSWORD_REQUIRED(2300, "Password cannot be blank", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT(2301, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_MISSING_UPPERCASE(2302, "ValidPassword must contain at least one uppercase letter", HttpStatus.BAD_REQUEST),
    PASSWORD_MISSING_LOWERCASE(2303, "ValidPassword must contain at least one lowercase letter", HttpStatus.BAD_REQUEST),
    PASSWORD_MISSING_NUMBER(2304, "ValidPassword must contain at least one digit", HttpStatus.BAD_REQUEST),
    PASSWORD_MISSING_SPECIAL_CHAR(2305, "ValidPassword must contain at least one special character", HttpStatus.BAD_REQUEST),
    // full name
    FULL_NAME_REQUIRED(2400, "full name cannot be blank", HttpStatus.BAD_REQUEST),
    // status
    STATUS_REQUIRED(2500, "status cannot be blank", HttpStatus.BAD_REQUEST),
    STATUS_INVALID_FORMAT(2501, "status format is invalid", HttpStatus.BAD_REQUEST),
    // gender
    GENDER_REQUIRED(2600, "gender cannot be blank", HttpStatus.BAD_REQUEST),
    // ========== END USER ==========
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
