package com.examly.springapp.exceptions;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.service.ErrorLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ErrorLogService errorLogService;

    public GlobalExceptionHandler(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }

    @ExceptionHandler({DriverDeletionException.class, DriverRequestDeletionException.class, DuplicateDriverException.class})
    public ResponseEntity<ErrorResponseDTO> handleConflict(RuntimeException ex, HttpServletRequest request) {
        String message = ex instanceof DuplicateDriverException
                ? "A driver with this license number already exists"
                : "The requested operation conflicts with existing data";
        return respond(HttpStatus.CONFLICT, message, request, Map.of(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage(),
                        (first, second) -> first, LinkedHashMap::new));
        return respond(HttpStatus.BAD_REQUEST, "Validation failed", request, fieldErrors, ex);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(Exception ex, HttpServletRequest request) {
        return respond(HttpStatus.BAD_REQUEST, "The request could not be processed. Please check the data and try again.",
                request, Map.of(), ex);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleDatabase(DataAccessException ex, HttpServletRequest request) {
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Please try again later.",
                request, Map.of(), ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAny(Exception ex, HttpServletRequest request) {
        HttpStatus status = ex instanceof ErrorResponse errorResponse
                ? HttpStatus.valueOf(errorResponse.getStatusCode().value())
                : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = status.is5xxServerError()
                ? "Something went wrong. Please try again later."
                : "The request could not be processed.";
        return respond(status, message, request, Map.of(), ex);
    }

    private ResponseEntity<ErrorResponseDTO> respond(HttpStatus status, String message,
            HttpServletRequest request, Map<String, String> errors, Exception ex) {
        logException(status, request, ex);
        try {
            errorLogService.record(status.value(), ex.getClass().getSimpleName(), message, request.getRequestURI());
        } catch (RuntimeException loggingFailure) {
            log.warn("Unable to persist handled exception type={} status={}", ex.getClass().getSimpleName(), status.value());
        }
        return ResponseEntity.status(status)
                .body(new ErrorResponseDTO(status.value(), message, request.getRequestURI(), errors));
    }

    private void logException(HttpStatus status, HttpServletRequest request, Exception ex) {
        if (status.is5xxServerError()) {
            log.error("Request failed: method={} path={} type={}", request.getMethod(), request.getRequestURI(),
                    ex.getClass().getSimpleName(), ex);
        } else {
            log.warn("Request rejected: method={} path={} type={}", request.getMethod(), request.getRequestURI(),
                    ex.getClass().getSimpleName());
        }
    }
}
