package com.example.todo.exception;

import com.example.todo.model.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException ex, Locale locale) {
        String msg = messageSource.getMessage("error.validation", null, locale);
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(BindException.class)
    public ApiResponse<Void> handleBindException(BindException ex, Locale locale) {
        String msg = messageSource.getMessage("error.validation", null, locale);
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException ex, Locale locale) {
        String msg = ex.getMessage();
        // 如果是国际化 key，则翻译
        if (msg != null && msg.startsWith("todo.")) {
            msg = messageSource.getMessage(msg, null, locale);
        }
        return ApiResponse.error(HttpStatus.NOT_FOUND.value(), msg);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception ex, Locale locale) {
        String msg = messageSource.getMessage("error.internal", null, locale);
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }
}
