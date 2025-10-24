package com.khahnm04.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(1002, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1004, "You do not have permission", HttpStatus.FORBIDDEN),
    VALIDATION_EXCEPTION(1005, "Validation error", HttpStatus.BAD_REQUEST),
    TOKEN_CREATION_FAILED(1007, "Token creation failed", HttpStatus.UNAUTHORIZED),

    // ========== 2xxx: USER ==========
    // user
    USER_NOT_FOUND(2000, "user not found", HttpStatus.NOT_FOUND),
    USER_NAME_EXISTED(2001, "username existed", HttpStatus.CONFLICT),
    USER_NAME_INVALID_LENGTH(2002, "Username must be between {min} and {max} characters", HttpStatus.BAD_REQUEST),
    USER_NAME_INVALID_FORMAT(2003, "Username can only contain letters, numbers and underscore", HttpStatus.BAD_REQUEST),
    USER_EXISTED(2004, "user already existed", HttpStatus.CONFLICT),
    // email
    EMAIL_EXISTED(2100, "email existed", HttpStatus.CONFLICT),
    EMAIL_REQUIRED(2101, "ValidEmail cannot be blank", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID_FORMAT(2102, "ValidEmail format is invalid", HttpStatus.BAD_REQUEST),
    // phone number
    PHONE_NUMBER_EXISTED(2200, "phone number existed", HttpStatus.CONFLICT),
    PHONE_NUMBER_REQUIRED(2201, "PhoneNumber number cannot be blank", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID_FORMAT(2202, "Phone number format is invalid", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID_LENGTH(2203, "Phone number must be exactly 10 digits", HttpStatus.BAD_REQUEST),
    EMAIL_OR_PHONE_NUMBER_REQUIRED(2204, "Email or phoneNumber number cannot be blank", HttpStatus.BAD_REQUEST),
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
    // genderEnum
    GENDER_REQUIRED(2600, "gender cannot be blank", HttpStatus.BAD_REQUEST),
    // ========== END USER ==========

    CATEGORY_NAME_REQUIRED(3000, "category name cannot be blank", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_EXISTED(3001, "category name existed", HttpStatus.CONFLICT),
    CATEGORY_PARENT_NOT_EXISTED(3002, "category parent not existed", HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND(3003, "category not found", HttpStatus.NOT_FOUND),
    CATEGORY_HAS_CHILDREN(3004, "Cannot delete category because it has child categories.", HttpStatus.BAD_REQUEST),


    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
