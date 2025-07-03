package com.example.todo.exception;

import com.example.todo.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException ex, Locale locale) {
        log.warn("Validation error: {}", ex.getMessage());
        String msg = messageSource.getMessage("error.validation", null, locale);
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(BindException.class)
    public ApiResponse<Void> handleBindException(BindException ex, Locale locale) {
        log.warn("Bind error: {}", ex.getMessage());
        String msg = messageSource.getMessage("error.validation", null, locale);
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(BusinessException ex, Locale locale) {
        log.warn("Business error: {}", ex.getMessage());
        String msg = ex.getMessage();
        if (msg != null && msg.startsWith("todo.")) {
            msg = messageSource.getMessage(msg, null, locale);
        }
        return ApiResponse.error(HttpStatus.NOT_FOUND.value(), msg);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException ex, Locale locale) {
        log.warn("Illegal argument: {}", ex.getMessage());
        String msg = ex.getMessage();
        if (msg != null && msg.startsWith("todo.")) {
            msg = messageSource.getMessage(msg, null, locale);
        }
        return ApiResponse.error(HttpStatus.NOT_FOUND.value(), msg);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception ex, Locale locale) {
        log.error("Internal error: {}", ex.getMessage(), ex);
        String msg = messageSource.getMessage("error.internal", null, locale);
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }
}
