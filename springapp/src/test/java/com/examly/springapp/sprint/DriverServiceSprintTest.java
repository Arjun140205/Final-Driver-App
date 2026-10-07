package com.examly.springapp.sprint;

import com.examly.springapp.exceptions.DuplicateDriverException;
import com.examly.springapp.dto.DriverDTO;
import com.examly.springapp.mapper.ApiMapper;
import com.examly.springapp.repository.DriverRepo;
import com.examly.springapp.service.DriverServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceSprintTest {
    @Mock private DriverRepo driverRepo;

    @Test
    void savesDriverWhenLicenseIsUnique() {
        DriverDTO driver = new DriverDTO();
        driver.setLicenseNumber("DL-2026-1234");
        when(driverRepo.existsByLicenseNumber(driver.getLicenseNumber())).thenReturn(false);
        when(driverRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        assertEquals(driver.getLicenseNumber(), new DriverServiceImpl(driverRepo, new ApiMapper())
                .addDriver(driver).getLicenseNumber());
        verify(driverRepo).save(any());
    }

    @Test
    void rejectsDuplicateLicenseNumberBeforeSaving() {
        DriverDTO driver = new DriverDTO();
        driver.setLicenseNumber("DL-2026-1234");
        when(driverRepo.existsByLicenseNumber(driver.getLicenseNumber())).thenReturn(true);

        assertThrows(DuplicateDriverException.class, () -> new DriverServiceImpl(driverRepo, new ApiMapper()).addDriver(driver));
        verify(driverRepo, never()).save(any());
    }
}
