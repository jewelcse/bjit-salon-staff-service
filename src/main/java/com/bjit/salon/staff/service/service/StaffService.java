package com.bjit.salon.staff.service.service;

import com.bjit.salon.staff.service.dto.request.StaffCreateDto;
import com.bjit.salon.staff.service.dto.request.StaffUpdateDto;
import com.bjit.salon.staff.service.dto.response.StaffResponseDto;

import java.util.List;

public interface StaffService {
    void createNewStaff(StaffCreateDto staffCreateDto);

    void updateStaff(StaffUpdateDto staffUpdateDto);

    StaffResponseDto getStaff(long id);

    List<StaffResponseDto> getAllStaff();

    List<StaffResponseDto> getListOfStaffBySalon(long id);

    List<StaffResponseDto> getListOfAvailableStaffBySalon(long id);

    boolean updateStaffAvailability(long id);
}
