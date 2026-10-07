package com.examly.springapp.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponseDTO {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final String path;
    private final Map<String, String> errors;

    public ErrorResponseDTO(int status, String message, String path, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.path = path;
        this.errors = errors;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public Map<String, String> getErrors() { return errors; }
}
