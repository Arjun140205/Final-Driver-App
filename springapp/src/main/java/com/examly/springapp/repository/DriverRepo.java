package com.examly.springapp.repository;

import com.examly.springapp.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
    boolean existsByLicenseNumber(String licenseNumber);
}
