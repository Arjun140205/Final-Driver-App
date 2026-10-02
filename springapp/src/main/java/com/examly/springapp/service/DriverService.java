package com.examly.springapp.service;

import com.examly.springapp.model.Driver;
import java.util.List;
import java.util.Optional;

public interface DriverService {
    Driver addDriver(Driver driver);
    Optional<Driver> getDriverById(Long driverId);
    List<Driver> getAllDrivers();
    Driver updateDriver(Long driverId, Driver driver);
    Driver deleteDriver(Long driverId);
}
