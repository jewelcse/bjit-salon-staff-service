package com.bjit.salon.staff.service.controller;

import com.bjit.salon.staff.service.dto.request.StaffCreateDto;
import com.bjit.salon.staff.service.dto.request.StaffUpdateDto;
import com.bjit.salon.staff.service.dto.response.StaffResponseDto;
import com.bjit.salon.staff.service.service.StaffService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.bjit.salon.staff.service.util.ConstraintsUtil.APPLICATION_BASE_URL;

@AllArgsConstructor
@RestController
@RequestMapping(APPLICATION_BASE_URL)
public class StaffController {

    private static final Logger log = LoggerFactory.getLogger(StaffController.class);
    private final StaffService staffService;
    @PostMapping("/staffs") // only admin can create a new staff
    public ResponseEntity<StaffResponseDto> create(@RequestBody StaffCreateDto staffCreateDto){
        log.info("Creating a new staff with user id: {}", staffCreateDto.getUserId());
        return new ResponseEntity<>(staffService.createNewStaff(staffCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/staffs") // only staff and admin can update
    public ResponseEntity<StaffResponseDto> update(@RequestBody StaffUpdateDto staffUpdateDto){
        log.info("Updating staff account details with user id: {}",staffUpdateDto.getUserId());
        return ResponseEntity.ok( staffService.updateStaff(staffUpdateDto));
    }

    @GetMapping("/staffs/{id}") // only staff and admin can view
    @CircuitBreaker(name = "staff-service", fallbackMethod = "getFallback")
    public ResponseEntity<StaffResponseDto> get(@PathVariable long id){
        log.info("Getting staff details with user id:{}",id);
        return ResponseEntity.ok(staffService.getStaff(id));
    }

    public ResponseEntity<StaffResponseDto> getFallback(Exception exception){
        return ResponseEntity.ok(new StaffResponseDto());
    }

    @GetMapping("/staffs/{id}/status") // only staff
    @CircuitBreaker(name = "staff-service", fallbackMethod = "updateStatusAvailabilityFallback")
    public ResponseEntity<String> updateStatusAvailability(@PathVariable("id") long id){
        boolean isAvailable = staffService.updateStaffAvailability(id);
        if (isAvailable){
            log.info("Update availability with staff id: {}", id);
            return ResponseEntity.ok("You are now available");
        }
        log.info("Update unAvailability with staff id: {}", id);
        return ResponseEntity.ok("You are unavailable");
    }

    public ResponseEntity<String> updateStatusAvailabilityFallback(Exception exception){
        return ResponseEntity.ok("Service is down");
    }

    @GetMapping("/staffs") // only super admin can view
    @CircuitBreaker(name = "staff-service", fallbackMethod = "getAllFallback")
    public ResponseEntity<List<StaffResponseDto>> getAll(){
        List<StaffResponseDto> allStaff = staffService.getAllStaff();
        log.info("Getting all staff with size: {}",allStaff.size());
        return ResponseEntity.ok(allStaff);
    }

    public ResponseEntity<List<StaffResponseDto>> getAllFallback(Exception exception){
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/salons/{id}/staffs")
    @CircuitBreaker(name = "staff-service", fallbackMethod = "getSalonStaffsFallback")
    public ResponseEntity<List<StaffResponseDto>> getSalonStaffs(@PathVariable long id){
        log.info("Getting all staff with salon id: {}",id);
        return ResponseEntity.ok(staffService.getListOfStaffBySalon(id));
    }

    public ResponseEntity<List<StaffResponseDto>> getSalonStaffsFallback(Exception exception){
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/salons/{id}/available/staffs")
    @CircuitBreaker(name = "staff-service", fallbackMethod = "getAvailableStaffFallback")
    public ResponseEntity<List<StaffResponseDto>> getAvailableStaff(@PathVariable long id){
        log.info("Getting all available staff with salon id: {}",id);
        return ResponseEntity.ok(staffService.getListOfAvailableStaffBySalon(id));
    }

    public ResponseEntity<List<StaffResponseDto>> getAvailableStaffFallback(Exception exception){
        return ResponseEntity.ok(new ArrayList<>());
    }

}
