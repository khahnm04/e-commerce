package com.khahnm04.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9000, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(9001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(9002, "You do not have permission", HttpStatus.FORBIDDEN),
    VALIDATION_EXCEPTION(9003, "Validation error", HttpStatus.BAD_REQUEST),
    TOKEN_CREATION_FAILED(9004, "Token creation failed", HttpStatus.UNAUTHORIZED),
    DUPLICATE_ENTRY(9005, "Duplicate entry", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(1000, "user not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(1001, "user already existed", HttpStatus.CONFLICT),
    EMAIL_OR_PHONE_NUMBER_REQUIRED(1002, "Email or phoneNumber number cannot be blank", HttpStatus.BAD_REQUEST),

    CATEGORY_NOT_FOUND(2000, "category not found", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(2001, "category already existed", HttpStatus.CONFLICT),
    CATEGORY_NAME_REQUIRED(2002, "category name cannot be blank", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_EXISTED(2003, "category name existed", HttpStatus.CONFLICT),
    CATEGORY_PARENT_NOT_EXISTED(2004, "category parent not existed", HttpStatus.CONFLICT),
    CATEGORY_HAS_CHILDREN(2005, "Cannot delete category because it has child categories.", HttpStatus.BAD_REQUEST),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatusCode statusCode;

}
