package com.bjit.salon.staff.service.repository;

import com.bjit.salon.staff.service.dto.response.StaffResponseDto;
import com.bjit.salon.staff.service.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    List<Staff> findAllBySalonId(long id);

    List<Staff> findAllBySalonIdAndIsAvailable(long id, boolean isAvailable);
}
