package com.examly.springapp.sprint;

import com.examly.springapp.dto.DriverDTO;
import com.examly.springapp.mapper.ApiMapper;
import com.examly.springapp.model.Driver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DriverMapperSprintTest {
    private final ApiMapper mapper = new ApiMapper();

    @Test
    void mapsDriverDtoToEntityAndBackWithExistingApiFields() {
        DriverDTO input = new DriverDTO();
        input.setDriverId(14L);
        input.setDriverName("Asha Driver");
        input.setLicenseNumber("DL-2026-1234");
        input.setExperienceYears(8);
        input.setContactNumber("9876543210");
        input.setAvailabilityStatus("Active");
        input.setAddress("Bengaluru");
        input.setVehicleType("Sedan");
        input.setHourlyRate(250.0);
        input.setImage("data:image/png;base64,AA==");

        DriverDTO output = mapper.toDTO(mapper.toEntity(input));

        assertEquals(input.getDriverId(), output.getDriverId());
        assertEquals(input.getLicenseNumber(), output.getLicenseNumber());
        assertEquals(input.getVehicleType(), output.getVehicleType());
        assertEquals(input.getImage(), output.getImage());
    }
}
