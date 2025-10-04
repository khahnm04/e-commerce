package com.khahnm04.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(2001, "user not found", HttpStatus.NOT_FOUND),
    USER_NAME_EXISTED(2002, "username existed", HttpStatus.CONFLICT),
    EMAIL_EXISTED(2003, "email existed", HttpStatus.CONFLICT),
    PHONE_NUMBER_EXISTED(2004, "phone number existed", HttpStatus.CONFLICT),
    USERNAME_INVALID_LENGTH(2005, "Username must be between {min} and {max} characters", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID_FORMAT(2006, "Username can only contain letters, numbers and underscore", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
