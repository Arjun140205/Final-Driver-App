package com.examly.springapp.sprint;

import com.examly.springapp.exceptions.DuplicateDriverException;
import com.examly.springapp.exceptions.GlobalExceptionHandler;
import com.examly.springapp.service.ErrorLogService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerSprintTest {
    @Test
    void duplicateDriverUsesSafeConflictResponseAndPersistsSummary() {
        ErrorLogService logs = mock(ErrorLogService.class);
        GlobalExceptionHandler handler = new GlobalExceptionHandler(logs);
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/driver");
        var response = handler.handleConflict(new DuplicateDriverException("duplicate"), request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("A driver with this license number already exists", response.getBody().getMessage());
        assertEquals("/api/driver", response.getBody().getPath());
        verify(logs).record(409, "DuplicateDriverException", "A driver with this license number already exists", "/api/driver");
    }
}
