package com.bjit.salon.staff.service.repository;

import com.bjit.salon.staff.service.entity.Staff;
import com.bjit.salon.staff.service.entity.StaffActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffActivityRepository extends JpaRepository<StaffActivity,Long> {

    Optional<StaffActivity> findByStaffIdAndReservationId(Long staffId, Long reservationId);

    List<StaffActivity> findAllByStaffId(long id);
}
