package com.examly.springapp.sprint;

import com.examly.springapp.dto.DriverDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DriverValidationSprintTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void rejectsMissingAndMalformedDriverInputs() {
        DriverDTO driver = new DriverDTO();
        assertFalse(validator.validate(driver).isEmpty());
        driver.setDriverName("Asha");
        driver.setLicenseNumber("x");
        driver.setExperienceYears(-1);
        driver.setContactNumber("abc");
        driver.setAddress("Bengaluru");
        driver.setVehicleType("Sedan");
        driver.setHourlyRate(0.0);
        assertFalse(validator.validate(driver).isEmpty());
    }

    @Test
    void acceptsAValidDriverPayload() {
        DriverDTO driver = new DriverDTO();
        driver.setDriverName("Asha Driver");
        driver.setLicenseNumber("DL-2026-1234");
        driver.setExperienceYears(5);
        driver.setContactNumber("9876543210");
        driver.setAddress("Bengaluru");
        driver.setVehicleType("Sedan");
        driver.setHourlyRate(250.0);
        assertTrue(validator.validate(driver).isEmpty());
    }
}
