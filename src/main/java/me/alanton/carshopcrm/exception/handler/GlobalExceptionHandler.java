package me.alanton.carshopcrm.exception.handler;

import me.alanton.carshopcrm.exception.impl.ApplicationException;
import me.alanton.carshopcrm.exception.impl.BusinessException;
import me.alanton.carshopcrm.exception.response.ErrorResponse;
import me.alanton.carshopcrm.exception.response.InvalidParameterResponse;
import me.alanton.carshopcrm.exception.util.ErrorResponseUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handleUncaughtException(
            final Exception ex,
            final ServletWebRequest request
    ) {
        final ErrorResponse errorResponse = ErrorResponseUtils.build(
                ex.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleCustomUncaughtBusinessException(
            final BusinessException ex,
            final ServletWebRequest request
    ) {
        final ErrorResponse errorResponse = ErrorResponseUtils.build(
                ex.getCode(),
                ex.getMessage(),
                ex.getStatus()
        );

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleCustomApplicationException(
            final ApplicationException ex,
            final ServletWebRequest request
    ) {
        final ErrorResponse errorResponse = ErrorResponseUtils.build(
                ex.getCode(),
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final List<InvalidParameterResponse> invalidParameter =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> InvalidParameterResponse.builder()
                                .parameter(fieldError.getField())
                                .message(fieldError.getDefaultMessage())
                                .build())
                        .toList();

        final ErrorResponse errorResponse = ErrorResponseUtils.build(
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST,
                invalidParameter
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}