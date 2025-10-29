package com.khahnm04.ecommerce.exception;

import com.khahnm04.ecommerce.dto.response.ErrorResponse;
import com.khahnm04.ecommerce.dto.response.FieldErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<String, ErrorCode> PATH_ERROR_MAP = Map.of(
            "/api/v1/users", ErrorCode.USER_EXISTED,
            "/api/v1/categories", ErrorCode.CATEGORY_EXISTED
    );

    // Catch exception when Duplicate entry
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    ResponseEntity<ErrorResponse> handlingDataIntegrityViolationException(DataIntegrityViolationException exception, HttpServletRequest request) {
        String path = request.getRequestURI();
        ErrorCode errorCode = PATH_ERROR_MAP.getOrDefault(path, ErrorCode.DUPLICATE_ENTRY);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .path(path)
                .method(request.getMethod())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ErrorResponse> handlingRuntimeException(RuntimeException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ErrorResponse> handlingAppException(AppException exception, HttpServletRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(errorResponse);
    }

    // Catch exception when Access Denied
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handlingAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(errorResponse);
    }

    // Catch exception when Validation
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handlingValidation(MethodArgumentNotValidException exception, HttpServletRequest request) {

        List<FieldErrorResponse> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> FieldErrorResponse.builder()
                            .field(fieldError.getField())
                            .message(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage())
                            .build())
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(ErrorCode.VALIDATION_EXCEPTION.getCode())
                .message(ErrorCode.VALIDATION_EXCEPTION.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
