package com.bjit.salon.staff.service.mapper;


import com.bjit.salon.staff.service.dto.listener.StaffActivityCreateDto;
import com.bjit.salon.staff.service.dto.request.StaffCreateDto;
import com.bjit.salon.staff.service.dto.request.StaffUpdateDto;
import com.bjit.salon.staff.service.dto.response.StaffResponseDto;
import com.bjit.salon.staff.service.entity.Staff;
import com.bjit.salon.staff.service.entity.StaffActivity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    Staff toStaff(StaffCreateDto staffCreateDto);
    Staff toStaff(StaffUpdateDto staffUpdateDto);

    StaffActivity toStaffActivity(StaffActivityCreateDto staffActivityCreateDto);
    StaffResponseDto toStaffResponseDto(Staff staff);
    List<StaffResponseDto> toListOfStaffResponseDto(List<Staff> staffs);
}
