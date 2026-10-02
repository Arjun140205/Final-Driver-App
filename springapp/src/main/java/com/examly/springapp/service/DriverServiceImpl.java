package com.examly.springapp.service;

import com.examly.springapp.exceptions.DriverDeletionException;
import com.examly.springapp.exceptions.DuplicateDriverException;
import com.examly.springapp.model.Driver;
import com.examly.springapp.repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepo driverRepo;

    @Override
    public Driver addDriver(Driver driver) {
        Optional<Driver> existingDriver = driverRepo.findByLicenseNumber(driver.getLicenseNumber());
        if (existingDriver.isPresent()) {
            throw new DuplicateDriverException("Driver with license number " + driver.getLicenseNumber() + " already exists.");
        }
        return driverRepo.save(driver);
    }

    @Override
    public Optional<Driver> getDriverById(Long driverId) {
        return driverRepo.findById(driverId);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepo.findAll();
    }

    @Override
    public Driver updateDriver(Long driverId, Driver driver) {
        Optional<Driver> existingDriverOpt = driverRepo.findById(driverId);
        if (existingDriverOpt.isPresent()) {
            Driver existingDriver = existingDriverOpt.get();
            existingDriver.setDriverName(driver.getDriverName());
            existingDriver.setLicenseNumber(driver.getLicenseNumber());
            existingDriver.setExperienceYears(driver.getExperienceYears());
            existingDriver.setContactNumber(driver.getContactNumber());
            existingDriver.setAvailabilityStatus(driver.getAvailabilityStatus());
            existingDriver.setAddress(driver.getAddress());
            existingDriver.setVehicleType(driver.getVehicleType());
            existingDriver.setHourlyRate(driver.getHourlyRate());
            existingDriver.setImage(driver.getImage());
            return driverRepo.save(existingDriver);
        }
        return null;
    }

    @Override
    public Driver deleteDriver(Long driverId) {
        Optional<Driver> existingDriver = driverRepo.findById(driverId);
        if (existingDriver.isPresent()) {
            try {
                driverRepo.delete(existingDriver.get());
                return existingDriver.get();
            } catch (Exception e) {
                throw new DriverDeletionException("Failed to delete driver with ID: " + driverId);
            }
        }
        return null;
    }
}
