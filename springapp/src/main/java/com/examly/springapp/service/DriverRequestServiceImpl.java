package com.examly.springapp.service;

import com.examly.springapp.exceptions.DriverRequestDeletionException;
import com.examly.springapp.model.DriverRequest;
import com.examly.springapp.repository.DriverRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DriverRequestServiceImpl implements DriverRequestService {

    @Autowired
    private DriverRequestRepo driverRequestRepo;

    @Override
    public DriverRequest addDriverRequest(DriverRequest driverRequest) {
        if (driverRequest.getRequestDate() == null) {
            driverRequest.setRequestDate(LocalDate.now());
        }
        return driverRequestRepo.save(driverRequest);
    }

    @Override
    public Optional<DriverRequest> getDriverRequestById(Long driverRequestId) {
        return driverRequestRepo.findById(driverRequestId);
    }

    @Override
    public List<DriverRequest> getAllDriverRequests() {
        return driverRequestRepo.findAll();
    }

    @Override
    public DriverRequest updateDriverRequest(Long driverRequestId, DriverRequest driverRequest) {
        Optional<DriverRequest> existingReqOpt = driverRequestRepo.findById(driverRequestId);
        if (existingReqOpt.isPresent()) {
            DriverRequest existingReq = existingReqOpt.get();
            if (driverRequest.getStatus() != null) existingReq.setStatus(driverRequest.getStatus());
            if (driverRequest.getTripDate() != null) existingReq.setTripDate(driverRequest.getTripDate());
            if (driverRequest.getTimeSlot() != null) existingReq.setTimeSlot(driverRequest.getTimeSlot());
            if (driverRequest.getPickupLocation() != null) existingReq.setPickupLocation(driverRequest.getPickupLocation());
            if (driverRequest.getDropLocation() != null) existingReq.setDropLocation(driverRequest.getDropLocation());
            if (driverRequest.getEstimatedDuration() != null) existingReq.setEstimatedDuration(driverRequest.getEstimatedDuration());
            if (driverRequest.getPaymentAmount() != null) existingReq.setPaymentAmount(driverRequest.getPaymentAmount());
            if (driverRequest.getComments() != null) existingReq.setComments(driverRequest.getComments());
            if (driverRequest.getActualDropTime() != null) existingReq.setActualDropTime(driverRequest.getActualDropTime());
            if (driverRequest.getActualDropDate() != null) existingReq.setActualDropDate(driverRequest.getActualDropDate());
            if (driverRequest.getActualDuration() != null) existingReq.setActualDuration(driverRequest.getActualDuration());
            if (driverRequest.getDriver() != null) existingReq.setDriver(driverRequest.getDriver());
            return driverRequestRepo.save(existingReq);
        }
        return null;
    }

    @Override
    public DriverRequest deleteDriverRequest(Long driverRequestId) {
        Optional<DriverRequest> existingReqOpt = driverRequestRepo.findById(driverRequestId);
        if (existingReqOpt.isPresent()) {
            try {
                driverRequestRepo.delete(existingReqOpt.get());
                return existingReqOpt.get();
            } catch (Exception e) {
                throw new DriverRequestDeletionException("Failed to delete driver request with ID: " + driverRequestId);
            }
        }
        return null;
    }

    @Override
    public List<DriverRequest> findDriverRequestsByUserId(Long userId) {
        return driverRequestRepo.findByUserUserId(userId);
    }

    @Override
    public List<DriverRequest> findDriverRequestsByDriverId(Long driverId) {
        return driverRequestRepo.findByDriverDriverId(driverId);
    }
}
