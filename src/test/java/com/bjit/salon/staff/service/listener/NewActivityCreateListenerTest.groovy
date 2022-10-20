package com.bjit.salon.staff.service.listener

import com.bjit.salon.staff.service.dto.listener.StaffActivityCreateDto
import com.bjit.salon.staff.service.entity.EWorkingStatus
import com.bjit.salon.staff.service.entity.StaffActivity
import com.bjit.salon.staff.service.mapper.StaffMapper
import com.bjit.salon.staff.service.repository.StaffActivityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

@SpringBootTest
class NewActivityCreateListenerTest extends Specification {

    private StaffActivityRepository staffActivityRepository;
    private NewActivityCreateListener activityListener;

    @Autowired
    private StaffMapper staffMapper;


    def setup() {
        staffActivityRepository = Mock()
        activityListener = new NewActivityCreateListener(staffActivityRepository, staffMapper)
    }

    def "should update the activity after listening from the specified topic"() {
        given:
        def activityRequest = StaffActivityCreateDto
                .builder()
                .staffId(1L)
                .consumerId(1L)
                .reservationId(1L)
                .workingStatus(EWorkingStatus.ALLOCATED)
                .startTime(LocalTime.parse("10:00:00"))
                .workingDate(LocalDate.parse("2022-10-10"))
                .build()

        def staffActivity = StaffActivity.builder()
                .id(1L)
                .staffId(1L)
                .consumerId(1L)
                .reservationId(1L)
                .workingStatus(EWorkingStatus.ALLOCATED)
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .workingDate(LocalDate.parse("2022-10-10"))
                .build()

        staffActivityRepository.findByStaffIdAndReservationId(1L, 1L) >> Optional.of(staffActivity)
        staffActivityRepository.save(_) >> staffActivity

        when:
        def response = activityListener.newActivityCreateListener(activityRequest)

        then:
        response.getConsumerId() == 1
        response.getReservationId() == 1
        response.getWorkingStatus() == EWorkingStatus.ALLOCATED
        response.getStaffId() == 1
        response.getWorkingDate() == LocalDate.parse("2022-10-10")
        response.getStartTime() == LocalTime.parse("10:00:00")
        response.getEndTime() == LocalTime.parse("12:00:00")
    }

    def "should create a new activity after listening from the specified topic"() {
        given:
        def activityRequest = StaffActivityCreateDto
                .builder()
                .staffId(1L)
                .consumerId(1L)
                .reservationId(1L)
                .workingStatus(EWorkingStatus.ALLOCATED)
                .startTime(LocalTime.parse("10:00:00"))
                .workingDate(LocalDate.parse("2022-10-10"))
                .build()


        def staffActivity = StaffActivity.builder()
                .id(1L)
                .staffId(1L)
                .consumerId(1L)
                .reservationId(1L)
                .workingStatus(EWorkingStatus.ALLOCATED)
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .workingDate(LocalDate.parse("2022-10-10"))
                .build()

        staffActivityRepository.findByStaffIdAndReservationId(1L, 1L) >> Optional.ofNullable(null)
        staffActivityRepository.save(_) >> staffActivity

        when:
        def response = activityListener.newActivityCreateListener(activityRequest)

        then:
        response.getConsumerId() == 1
        response.getReservationId() == 1
        response.getWorkingStatus() == EWorkingStatus.ALLOCATED
        response.getStaffId() == 1
        response.getWorkingDate() == LocalDate.parse("2022-10-10")
        response.getStartTime() == LocalTime.parse("10:00:00")
        response.getEndTime() == LocalTime.parse("12:00:00")
    }
}
