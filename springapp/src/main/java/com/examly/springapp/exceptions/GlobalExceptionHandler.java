package com.examly.springapp.exceptions;

import com.examly.springapp.model.ErrorLog;
import com.examly.springapp.repository.ErrorLogRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Central exception handling: every handled exception is logged and stored in the
 * "ErrorLogs" table, and the client gets a short, user-friendly message.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ErrorLogRepo errorLogRepo;

    @ExceptionHandler({DriverDeletionException.class, DriverRequestDeletionException.class, DuplicateDriverException.class})
    public ResponseEntity<Map<String, String>> handleConflict(RuntimeException ex, HttpServletRequest request) {
        return respond(HttpStatus.CONFLICT, ex, ex.getMessage(), request);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> handleBadRequest(Exception ex, HttpServletRequest request) {
        return respond(HttpStatus.BAD_REQUEST, ex, "The request could not be processed. Please check the data and try again.", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAny(Exception ex, HttpServletRequest request) {
        // Standard Spring MVC errors (405, 415, ...) keep their own status code.
        HttpStatusCode status = ex instanceof ErrorResponse errorResponse
                ? errorResponse.getStatusCode()
                : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = status.is5xxServerError() ? "Something went wrong. Please try again later." : ex.getMessage();
        return respond(status, ex, message, request);
    }

    private ResponseEntity<Map<String, String>> respond(HttpStatusCode status, Exception ex, String message, HttpServletRequest request) {
        log.error("{} {} -> {}: {}", request.getMethod(), request.getRequestURI(), ex.getClass().getSimpleName(), ex.getMessage());
        try {
            errorLogRepo.save(new ErrorLog(status.value(), ex.getClass().getName(), ex.getMessage(), request.getRequestURI()));
        } catch (Exception loggingFailure) {
            log.warn("Could not store the error log: {}", loggingFailure.getMessage());
        }
        return ResponseEntity.status(status).body(Map.of("message", message == null ? "Unexpected error" : message));
    }
}
