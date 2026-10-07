package com.examly.springapp.service;

import com.examly.springapp.model.ErrorLog;
import com.examly.springapp.repository.ErrorLogRepo;
import org.springframework.stereotype.Service;

@Service
public class ErrorLogServiceImpl implements ErrorLogService {
    private final ErrorLogRepo errorLogRepo;

    public ErrorLogServiceImpl(ErrorLogRepo errorLogRepo) {
        this.errorLogRepo = errorLogRepo;
    }

    @Override
    public void record(int status, String exceptionType, String message, String path) {
        // Store only sanitized summaries; raw exception messages can contain submitted values or SQL.
        String safeMessage = status >= 500 ? "Unexpected server error" : "Request could not be processed";
        errorLogRepo.save(new ErrorLog(status, exceptionType, safeMessage, path));
    }
}
