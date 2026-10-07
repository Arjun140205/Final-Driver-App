package com.examly.springapp.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/** Maintains the existing nested user/driver JSON shape while avoiding JPA serialization. */
public class DriverRequestDTO {
    private Long driverRequestId;
    @NotNull private UserDTO user;
    private DriverDTO driver;
    private LocalDate requestDate;
    private String status;
    private LocalDate tripDate;
    private LocalTime timeSlot;
    private String pickupLocation;
    private String dropLocation;
    private String estimatedDuration;
    private Double paymentAmount;
    private String comments;
    private LocalTime actualDropTime;
    private LocalDate actualDropDate;
    private String actualDuration;

    public Long getDriverRequestId() { return driverRequestId; }
    public void setDriverRequestId(Long driverRequestId) { this.driverRequestId = driverRequestId; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    public DriverDTO getDriver() { return driver; }
    public void setDriver(DriverDTO driver) { this.driver = driver; }
    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getTripDate() { return tripDate; }
    public void setTripDate(LocalDate tripDate) { this.tripDate = tripDate; }
    public LocalTime getTimeSlot() { return timeSlot; }
    public void setTimeSlot(LocalTime timeSlot) { this.timeSlot = timeSlot; }
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public String getDropLocation() { return dropLocation; }
    public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }
    public String getEstimatedDuration() { return estimatedDuration; }
    public void setEstimatedDuration(String estimatedDuration) { this.estimatedDuration = estimatedDuration; }
    public Double getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(Double paymentAmount) { this.paymentAmount = paymentAmount; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public LocalTime getActualDropTime() { return actualDropTime; }
    public void setActualDropTime(LocalTime actualDropTime) { this.actualDropTime = actualDropTime; }
    public LocalDate getActualDropDate() { return actualDropDate; }
    public void setActualDropDate(LocalDate actualDropDate) { this.actualDropDate = actualDropDate; }
    public String getActualDuration() { return actualDuration; }
    public void setActualDuration(String actualDuration) { this.actualDuration = actualDuration; }
}
