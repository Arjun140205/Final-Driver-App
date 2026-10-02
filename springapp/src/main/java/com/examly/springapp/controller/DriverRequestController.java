package com.examly.springapp.controller;

import com.examly.springapp.model.DriverRequest;
import com.examly.springapp.service.DriverRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DriverRequestController {

    @Autowired
    private DriverRequestService driverRequestService;

    @PostMapping("/driverRequest")
    public ResponseEntity<DriverRequest> addDriverRequest(@RequestBody DriverRequest driverRequest) {
        try {
            DriverRequest savedRequest = driverRequestService.addDriverRequest(driverRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/driverRequest/{driverRequestId}")
    public ResponseEntity<DriverRequest> viewDriverRequestById(@PathVariable Long driverRequestId) {
        Optional<DriverRequest> reqOpt = driverRequestService.getDriverRequestById(driverRequestId);
        if (reqOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(reqOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/driverRequest/user/{userId}")
    public ResponseEntity<List<DriverRequest>> viewDriverRequestsByUserId(@PathVariable Long userId) {
        List<DriverRequest> requests = driverRequestService.findDriverRequestsByUserId(userId);
        if (requests == null || requests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }

    @GetMapping("/driverRequest")
    public ResponseEntity<List<DriverRequest>> viewAllDriverRequests() {
        List<DriverRequest> requests = driverRequestService.getAllDriverRequests();
        if (requests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }

    @PutMapping("/driverRequest/{driverRequestId}")
    public ResponseEntity<DriverRequest> updateDriverRequest(@PathVariable Long driverRequestId, @RequestBody DriverRequest driverRequest) {
        DriverRequest updated = driverRequestService.updateDriverRequest(driverRequestId, driverRequest);
        if (updated != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/driverRequest/driver/{driverId}")
    public ResponseEntity<List<DriverRequest>> viewDriverRequestsByDriverId(@PathVariable Long driverId) {
        List<DriverRequest> requests = driverRequestService.findDriverRequestsByDriverId(driverId);
        if (requests == null || requests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(requests);
    }

    @DeleteMapping("/driverRequest/{driverRequestId}")
    public ResponseEntity<DriverRequest> deleteDriverRequest(@PathVariable Long driverRequestId) {
        DriverRequest deleted = driverRequestService.deleteDriverRequest(driverRequestId);
        if (deleted != null) {
            return ResponseEntity.status(HttpStatus.OK).body(deleted);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
