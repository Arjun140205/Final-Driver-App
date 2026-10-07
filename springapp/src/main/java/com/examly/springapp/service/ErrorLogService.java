package com.examly.springapp.service;

public interface ErrorLogService {
    void record(int status, String exceptionType, String message, String path);
}
