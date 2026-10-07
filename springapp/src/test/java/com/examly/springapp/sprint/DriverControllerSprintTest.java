package com.examly.springapp.sprint;

import com.examly.springapp.controller.DriverController;
import com.examly.springapp.dto.DriverDTO;
import com.examly.springapp.mapper.ApiMapper;
import com.examly.springapp.dto.DriverDTO;
import com.examly.springapp.service.DriverService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DriverControllerSprintTest {
    @Test
    void postDriverKeepsCreatedStatusAndJsonFieldContractThroughDto() {
        DriverService service = mock(DriverService.class);
        DriverDTO persisted = new DriverDTO();
        persisted.setDriverId(9L);
        persisted.setDriverName("Asha Driver");
        persisted.setLicenseNumber("DL-2026-1234");
        when(service.addDriver(any(DriverDTO.class))).thenReturn(persisted);

        DriverDTO request = new DriverDTO();
        request.setDriverName("Asha Driver");
        request.setLicenseNumber("DL-2026-1234");
        var response = new DriverController(service).addDriver(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(9L, response.getBody().getDriverId());
        assertEquals("DL-2026-1234", response.getBody().getLicenseNumber());
    }
}
