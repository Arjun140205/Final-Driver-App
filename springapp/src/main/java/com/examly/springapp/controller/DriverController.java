package com.examly.springapp.controller;

import com.examly.springapp.model.Driver;
import com.examly.springapp.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/driver")
    public ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
        try {
            Driver savedDriver = driverService.addDriver(driver);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Driver> viewDriverById(@PathVariable Long driverId) {
        Optional<Driver> driverOpt = driverService.getDriverById(driverId);
        if (driverOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(driverOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/driver")
    public ResponseEntity<List<Driver>> viewAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        if (drivers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }

    @PutMapping("/driver/{driverId}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long driverId, @RequestBody Driver driver) {
        Driver updatedDriver = driverService.updateDriver(driverId, driver);
        if (updatedDriver != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedDriver);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/driver/{driverId}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable Long driverId) {
        Driver deletedDriver = driverService.deleteDriver(driverId);
        if (deletedDriver != null) {
            return ResponseEntity.status(HttpStatus.OK).body(deletedDriver);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
