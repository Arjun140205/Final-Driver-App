package com.examly.springapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/** API representation of a driver. Field names match the existing JSON contract. */
public class DriverDTO {
    private Long driverId;
    @NotBlank private String driverName;
    @NotBlank @Pattern(regexp = "[A-Za-z0-9 /-]{5,25}") private String licenseNumber;
    @NotNull @Min(0) @Max(60) private Integer experienceYears;
    @NotBlank @Pattern(regexp = "\\+?\\d{10,13}") private String contactNumber;
    private String availabilityStatus;
    @NotBlank private String address;
    @NotBlank private String vehicleType;
    @NotNull @DecimalMin("0.01") private Double hourlyRate;
    private String image;

    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
