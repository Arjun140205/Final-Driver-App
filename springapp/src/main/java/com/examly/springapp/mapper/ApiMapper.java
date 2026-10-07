package com.examly.springapp.mapper;

import com.examly.springapp.dto.DriverDTO;
import com.examly.springapp.dto.DriverRequestDTO;
import com.examly.springapp.dto.FeedbackDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.model.Driver;
import com.examly.springapp.model.DriverRequest;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/** Explicit API/persistence mapping keeps JPA relationships out of JSON serialization. */
@Component
public class ApiMapper {
    public DriverDTO toDTO(Driver source) {
        if (source == null) return null;
        DriverDTO target = new DriverDTO();
        target.setDriverId(source.getDriverId());
        target.setDriverName(source.getDriverName());
        target.setLicenseNumber(source.getLicenseNumber());
        target.setExperienceYears(source.getExperienceYears());
        target.setContactNumber(source.getContactNumber());
        target.setAvailabilityStatus(source.getAvailabilityStatus());
        target.setAddress(source.getAddress());
        target.setVehicleType(source.getVehicleType());
        target.setHourlyRate(source.getHourlyRate());
        target.setImage(source.getImage());
        return target;
    }

    public Driver toEntity(DriverDTO source) {
        if (source == null) return null;
        Driver target = new Driver();
        target.setDriverId(source.getDriverId());
        target.setDriverName(source.getDriverName());
        target.setLicenseNumber(source.getLicenseNumber());
        target.setExperienceYears(source.getExperienceYears());
        target.setContactNumber(source.getContactNumber());
        target.setAvailabilityStatus(source.getAvailabilityStatus());
        target.setAddress(source.getAddress());
        target.setVehicleType(source.getVehicleType());
        target.setHourlyRate(source.getHourlyRate());
        target.setImage(source.getImage());
        return target;
    }

    public List<DriverDTO> toDriverDTOs(List<Driver> sources) {
        return sources.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserDTO toDTO(User source) {
        if (source == null) return null;
        UserDTO target = new UserDTO();
        target.setUserId(source.getUserId());
        target.setEmail(source.getEmail());
        target.setUsername(source.getUsername());
        target.setMobileNumber(source.getMobileNumber());
        target.setUserRole(source.getUserRole());
        return target;
    }

    public User toEntity(UserDTO source) {
        if (source == null) return null;
        User target = new User();
        target.setUserId(source.getUserId());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setUsername(source.getUsername());
        target.setMobileNumber(source.getMobileNumber());
        target.setUserRole(source.getUserRole());
        return target;
    }

    public DriverRequestDTO toDTO(DriverRequest source) {
        if (source == null) return null;
        DriverRequestDTO target = new DriverRequestDTO();
        target.setDriverRequestId(source.getDriverRequestId());
        target.setUser(toDTO(source.getUser()));
        target.setDriver(toDTO(source.getDriver()));
        target.setRequestDate(source.getRequestDate());
        target.setStatus(source.getStatus());
        target.setTripDate(source.getTripDate());
        target.setTimeSlot(source.getTimeSlot());
        target.setPickupLocation(source.getPickupLocation());
        target.setDropLocation(source.getDropLocation());
        target.setEstimatedDuration(source.getEstimatedDuration());
        target.setPaymentAmount(source.getPaymentAmount());
        target.setComments(source.getComments());
        target.setActualDropTime(source.getActualDropTime());
        target.setActualDropDate(source.getActualDropDate());
        target.setActualDuration(source.getActualDuration());
        return target;
    }

    public DriverRequest toEntity(DriverRequestDTO source) {
        if (source == null) return null;
        DriverRequest target = new DriverRequest();
        target.setDriverRequestId(source.getDriverRequestId());
        if (source.getUser() != null) {
            User user = new User();
            user.setUserId(source.getUser().getUserId());
            target.setUser(user);
        }
        if (source.getDriver() != null) {
            Driver driver = new Driver();
            driver.setDriverId(source.getDriver().getDriverId());
            target.setDriver(driver);
        }
        target.setRequestDate(source.getRequestDate());
        target.setStatus(source.getStatus());
        target.setTripDate(source.getTripDate());
        target.setTimeSlot(source.getTimeSlot());
        target.setPickupLocation(source.getPickupLocation());
        target.setDropLocation(source.getDropLocation());
        target.setEstimatedDuration(source.getEstimatedDuration());
        target.setPaymentAmount(source.getPaymentAmount());
        target.setComments(source.getComments());
        target.setActualDropTime(source.getActualDropTime());
        target.setActualDropDate(source.getActualDropDate());
        target.setActualDuration(source.getActualDuration());
        return target;
    }

    public List<DriverRequestDTO> toDriverRequestDTOs(List<DriverRequest> sources) {
        return sources.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FeedbackDTO toDTO(Feedback source) {
        if (source == null) return null;
        FeedbackDTO target = new FeedbackDTO();
        target.setFeedbackId(source.getFeedbackId());
        target.setFeedbackText(source.getFeedbackText());
        target.setDate(source.getDate());
        target.setUser(toDTO(source.getUser()));
        target.setDriver(toDTO(source.getDriver()));
        target.setCategory(source.getCategory());
        target.setRating(source.getRating());
        target.setSentiment(source.getSentiment());
        target.setSentimentScore(source.getSentimentScore());
        target.setAiTags(source.getAiTags());
        return target;
    }

    public Feedback toEntity(FeedbackDTO source) {
        if (source == null) return null;
        Feedback target = new Feedback();
        target.setFeedbackId(source.getFeedbackId());
        target.setFeedbackText(source.getFeedbackText());
        target.setDate(source.getDate());
        if (source.getUser() != null) {
            User user = new User();
            user.setUserId(source.getUser().getUserId());
            target.setUser(user);
        }
        if (source.getDriver() != null) {
            Driver driver = new Driver();
            driver.setDriverId(source.getDriver().getDriverId());
            target.setDriver(driver);
        }
        target.setCategory(source.getCategory());
        target.setRating(source.getRating());
        target.setSentiment(source.getSentiment());
        target.setSentimentScore(source.getSentimentScore());
        target.setAiTags(source.getAiTags());
        return target;
    }

    public List<FeedbackDTO> toFeedbackDTOs(List<Feedback> sources) {
        return sources.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
