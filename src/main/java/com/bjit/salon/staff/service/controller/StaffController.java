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
    @PostMapping("/staffs")
    public ResponseEntity<StaffResponseDto> create(@RequestBody StaffCreateDto staffCreateDto){
        log.info("Creating a new staff with user id: {}", staffCreateDto.getUserId());
        return new ResponseEntity<>(staffService.createNewStaff(staffCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/staffs")
    public ResponseEntity<StaffResponseDto> update(@RequestBody StaffUpdateDto staffUpdateDto){
        log.info("Updating staff account details with user id: {}",staffUpdateDto.getUserId());
        return ResponseEntity.ok( staffService.updateStaff(staffUpdateDto));
    }

    @GetMapping("/staffs/{id}")
    @CircuitBreaker(name = "staff-service", fallbackMethod = "getFallback")
    public ResponseEntity<StaffResponseDto> get(@PathVariable long id){
        log.info("Getting staff details with user id:{}",id);
        return ResponseEntity.ok(staffService.getStaff(id));
    }

    @GetMapping("/staffs/{id}/activities")
    public ResponseEntity<StaffResponseDto> getStaffActivity(@PathVariable long id){
        log.info("Getting staff activities with user id:{}",id);
        return ResponseEntity.ok(staffService.getStaff(id));
    }

    @GetMapping("/staffs/{id}/status")
    public ResponseEntity<String> updateStatusAvailability(@PathVariable("id") long id){
        boolean isAvailable = staffService.updateStaffAvailability(id);
        if (isAvailable){
            log.info("Update availability with staff id: {}", id);
            return ResponseEntity.ok("You are now available");
        }
        log.info("Update unAvailability with staff id: {}", id);
        return ResponseEntity.ok("You are unavailable");
    }

    @GetMapping("/staffs")
    public ResponseEntity<List<StaffResponseDto>> getAll(){
        List<StaffResponseDto> allStaff = staffService.getAllStaff();
        log.info("Getting all staff with size: {}",allStaff.size());
        return ResponseEntity.ok(allStaff);
    }

    @GetMapping("/salons/{id}/staffs")
    public ResponseEntity<List<StaffResponseDto>> getSalonStaffs(@PathVariable long id){
        log.info("Getting all staff with salon id: {}",id);
        return ResponseEntity.ok(staffService.getListOfStaffBySalon(id));
    }

    @GetMapping("/salons/{id}/available/staffs")
    public ResponseEntity<List<StaffResponseDto>> getAvailableStaff(@PathVariable long id){
        log.info("Getting all available staff with salon id: {}",id);
        return ResponseEntity.ok(staffService.getListOfAvailableStaffBySalon(id));
    }

}
