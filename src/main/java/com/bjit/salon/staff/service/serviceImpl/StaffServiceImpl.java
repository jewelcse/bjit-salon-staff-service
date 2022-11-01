package com.bjit.salon.staff.service.serviceImpl;

import com.bjit.salon.staff.service.dto.request.StaffCreateDto;
import com.bjit.salon.staff.service.dto.request.StaffUpdateDto;
import com.bjit.salon.staff.service.dto.response.StaffResponseDto;
import com.bjit.salon.staff.service.entity.Staff;
import com.bjit.salon.staff.service.entity.StaffActivity;
import com.bjit.salon.staff.service.exception.StaffNotFoundException;
import com.bjit.salon.staff.service.mapper.StaffMapper;
import com.bjit.salon.staff.service.repository.StaffActivityRepository;
import com.bjit.salon.staff.service.repository.StaffRepository;
import com.bjit.salon.staff.service.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffActivityRepository staffActivityRepository;
    private final StaffMapper staffMapper;
    @Override
    public StaffResponseDto createNewStaff(StaffCreateDto staffCreateDto) {
        // todo: add new staff role to the user since now this user belong to staff
        Staff staff = staffRepository.save(staffMapper.toStaff(staffCreateDto)); //mock -> staff
        return staffMapper.toStaffResponseDto(staff);
    }

    @Override
    public StaffResponseDto updateStaff(StaffUpdateDto staffUpdateDto) {
        Optional<Staff> staff = staffRepository.findById(staffUpdateDto.getId());
        if (staff.isEmpty()){
            throw new StaffNotFoundException("staff not found for id: " + staffUpdateDto.getId());
        }
        Staff updateStaff = Staff.builder()
                .address(staffUpdateDto.getAddress())
                .id(staff.get().getId())
                .salonId(staffUpdateDto.getSalonId())
                .userId(staffUpdateDto.getUserId())
                .salary(staffUpdateDto.getSalary())
                .employmentDate(staffUpdateDto.getEmploymentDate())
                .employmentType(staffUpdateDto.getEmploymentType())
                .contractNumber(staffUpdateDto.getContractNumber())
                        .build();
        Staff updatedStaff = staffRepository.save(updateStaff);
        return staffMapper.toStaffResponseDto(updatedStaff);
    }

    @Override
    public StaffResponseDto getStaff(long id) {
        Optional<Staff> staff = staffRepository.findById(id); // mock
        if (staff.isEmpty()){
            throw new StaffNotFoundException("Staff not found for id: "+ id);
        }
        List<StaffActivity> activities = staffActivityRepository.findAllByStaffId(id); // mock
        return StaffResponseDto.builder()
                .salonId(staff.get().getSalonId())
                .userId(staff.get().getUserId())
                .address(staff.get().getAddress())
                .contractNumber(staff.get().getContractNumber())
                .isAvailable(staff.get().isAvailable())
                .employmentDate(staff.get().getEmploymentDate())
                .employmentType(staff.get().getEmploymentType())
                .activities(activities)
                .build();
    }

    @Override
    public List<StaffResponseDto> getAllStaff() {
        return staffMapper.toListOfStaffResponseDto(staffRepository.findAll());
    }

    @Override
    public List<StaffResponseDto> getListOfStaffBySalon(long id) {
        return staffMapper.toListOfStaffResponseDto(staffRepository.findAllBySalonId(id));
    }

    @Override
    public List<StaffResponseDto> getListOfAvailableStaffBySalon(long id) {
        return staffMapper.toListOfStaffResponseDto(staffRepository.findAllBySalonIdAndIsAvailable(id,true));
    }

    @Override
    public boolean updateStaffAvailability(long id) {
        Optional<Staff> staff = staffRepository.findById(id); // mock
        if (staff.isEmpty()){
            throw new StaffNotFoundException("staff not found for id:"+id);
        }
        if (staff.get().isAvailable()){
            staff.get().setAvailable(false);
            staffRepository.save(staff.get()); // mock
            return false;
        }
        staff.get().setAvailable(true);
        staffRepository.save(staff.get()); // mock
        return true;
    }
}
