package com.bjit.salon.staff.service.listener;


import com.bjit.salon.staff.service.dto.listener.StaffActivityCreateDto;
import com.bjit.salon.staff.service.entity.Staff;
import com.bjit.salon.staff.service.entity.StaffActivity;
import com.bjit.salon.staff.service.exception.StaffNotFoundException;
import com.bjit.salon.staff.service.mapper.StaffMapper;
import com.bjit.salon.staff.service.repository.StaffActivityRepository;
import com.bjit.salon.staff.service.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.bjit.salon.staff.service.util.ConstraintsUtil.STAFF_NEW_ACTIVITY_GROUP;
import static com.bjit.salon.staff.service.util.ConstraintsUtil.STAFF_NEW_ACTIVITY_TOPIC;

@Service
@RequiredArgsConstructor
public class NewActivityCreateListener {

    private final static Logger log = LoggerFactory.getLogger(NewActivityCreateListener.class);
    private final StaffActivityRepository staffActivityRepository;
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;



    @KafkaListener(
            topics = STAFF_NEW_ACTIVITY_TOPIC,
            groupId = STAFF_NEW_ACTIVITY_GROUP
    )
    public void newActivityCreateListener(StaffActivityCreateDto activityCreateDto) {
        log.info("consumer: "+activityCreateDto.toString());

        Optional<StaffActivity> hasActivity = staffActivityRepository.
                findByStaffIdAndReservationId(activityCreateDto.getStaffId(), activityCreateDto.getReservationId());

        if (hasActivity.isEmpty()){
            staffActivityRepository.save(staffMapper.toStaffActivity(activityCreateDto));
        }else{
            hasActivity.get().setWorkingStatus(activityCreateDto.getWorkingStatus());
            hasActivity.get().setReservationId(activityCreateDto.getReservationId());
            hasActivity.get().setStaffId(activityCreateDto.getStaffId());
            staffActivityRepository.save(hasActivity.get());
        }

    }
}
