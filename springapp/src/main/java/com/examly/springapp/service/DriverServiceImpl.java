package com.examly.springapp.service;

import com.examly.springapp.exceptions.DriverDeletionException;
import com.examly.springapp.exceptions.DuplicateDriverException;
import com.examly.springapp.dto.DriverDTO;
import com.examly.springapp.mapper.ApiMapper;
import com.examly.springapp.model.Driver;
import com.examly.springapp.repository.DriverRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;
    private final ApiMapper mapper;

    public DriverServiceImpl(DriverRepo driverRepo, ApiMapper mapper) {
        this.driverRepo = driverRepo;
        this.mapper = mapper;
    }

    @Override
    public DriverDTO addDriver(DriverDTO driverDTO) {
        Driver driver = mapper.toEntity(driverDTO);
        if (driverRepo.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new DuplicateDriverException("Driver with license number " + driver.getLicenseNumber() + " already exists.");
        }
        return mapper.toDTO(driverRepo.save(driver));
    }

    @Override
    public Optional<DriverDTO> getDriverById(Long driverId) {
        return driverRepo.findById(driverId).map(mapper::toDTO);
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        return mapper.toDriverDTOs(driverRepo.findAll());
    }

    @Override
    public DriverDTO updateDriver(Long driverId, DriverDTO driverDTO) {
        Driver driver = mapper.toEntity(driverDTO);
        Optional<Driver> existingDriverOpt = driverRepo.findById(driverId);
        if (existingDriverOpt.isPresent()) {
            Driver existingDriver = existingDriverOpt.get();
            if (!existingDriver.getLicenseNumber().equals(driver.getLicenseNumber())
                    && driverRepo.existsByLicenseNumber(driver.getLicenseNumber())) {
                throw new DuplicateDriverException("Driver with license number " + driver.getLicenseNumber() + " already exists.");
            }
            existingDriver.setDriverName(driver.getDriverName());
            existingDriver.setLicenseNumber(driver.getLicenseNumber());
            existingDriver.setExperienceYears(driver.getExperienceYears());
            existingDriver.setContactNumber(driver.getContactNumber());
            existingDriver.setAvailabilityStatus(driver.getAvailabilityStatus());
            existingDriver.setAddress(driver.getAddress());
            existingDriver.setVehicleType(driver.getVehicleType());
            existingDriver.setHourlyRate(driver.getHourlyRate());
            existingDriver.setImage(driver.getImage());
            return mapper.toDTO(driverRepo.save(existingDriver));
        }
        return null;
    }

    @Override
    public DriverDTO deleteDriver(Long driverId) {
        Optional<Driver> existingDriver = driverRepo.findById(driverId);
        if (existingDriver.isPresent()) {
            try {
                driverRepo.delete(existingDriver.get());
                return mapper.toDTO(existingDriver.get());
            } catch (Exception e) {
                throw new DriverDeletionException("Failed to delete driver with ID: " + driverId);
            }
        }
        return null;
    }
}
