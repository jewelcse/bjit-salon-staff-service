package com.bjit.salon.staff.service.serviceImpl

import com.bjit.salon.staff.service.dto.request.StaffCreateDto
import com.bjit.salon.staff.service.dto.request.StaffUpdateDto
import com.bjit.salon.staff.service.entity.EWorkingStatus
import com.bjit.salon.staff.service.entity.Staff
import com.bjit.salon.staff.service.entity.StaffActivity
import com.bjit.salon.staff.service.exception.StaffNotFoundException
import com.bjit.salon.staff.service.mapper.StaffMapper
import com.bjit.salon.staff.service.repository.StaffActivityRepository
import com.bjit.salon.staff.service.repository.StaffRepository
import com.bjit.salon.staff.service.serviceImpl.StaffServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class StaffServiceImplApplicationUnitTest extends Specification {


    private StaffRepository staffRepository
    private StaffActivityRepository staffActivityRepository
    private StaffServiceImpl staffService
    @Autowired
    private StaffMapper staffMapper;


    def setup() {
        staffRepository = Mock(StaffRepository)
        staffActivityRepository = Mock(StaffActivityRepository)
        staffService = new StaffServiceImpl(staffRepository, staffActivityRepository, staffMapper)
    }


    def "should create a new staff"() {

        given:
        def staffRequest = StaffCreateDto
                .builder()
                .salonId(1L)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        def staff = Staff
                .builder()
                .salonId(1L)
                .isAvailable(true)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        staffRepository.save(_) >> staff

        when:
        def staffResponseDto = staffService.createNewStaff(staffRequest)

        then:
        staffResponseDto.getUserId() == 1
    }

    def "should update the staff details"() {

        given:
        def staff = Staff
                .builder()
                .id(1L)
                .salonId(1L)
                .isAvailable(true)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        def staffRequest = StaffUpdateDto
                .builder()
                .id(1L)
                .salonId(1L)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        staffRepository.findById(1L) >> Optional.of(staff)
        staffRepository.save(_) >> staff

        when:
        def staffResponse = staffService.updateStaff(staffRequest)

        then:
        staffResponse.getAddress() == "dhaka"
        staffResponse.getSalary() == (double) 4000.0

    }

    def "should throw staff not found exception while updating the staff details"(){
        given:
        def staffRequest = StaffUpdateDto
                .builder()
                .id(2L)
                .salonId(1L)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()
        staffRepository.findById(2L) >> Optional.ofNullable(null)

        when:
        staffService.updateStaff(staffRequest)

        then:
        def exception = thrown(StaffNotFoundException)
        exception.message == "staff not found for id: 2"
    }

    def "should return a staff object with activities by staff id"() {
        given:
        def staff = Staff
                .builder()
                .salonId(1L)
                .isAvailable(true)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        def staffActivity = StaffActivity
                .builder()
                .id(1L)
                .startTime(null)
                .consumerId(1L)
                .endTime(null)
                .reservationId(1L)
                .staffId(1L)
                .workingDate(null)
                .workingStatus(EWorkingStatus.ALLOCATED)
                .build()

        staffRepository.findById(1L) >> Optional.of(staff)
        staffActivityRepository.findAllByStaffId(1L) >> [staffActivity,staffActivity]

        when:
        def staffResponseDto = staffService.getStaff(1L)

        then:
        staffResponseDto.getActivities().size() == 2
        staffResponseDto.getActivities().get(0).getWorkingStatus() == EWorkingStatus.ALLOCATED

    }

    def "should throw staff not found exception while getting the staff object by staff id"(){

        given:
        staffRepository.findById(2L) >> Optional.ofNullable(null)

        when:
        staffService.getStaff(2L)

        then:
        def exception = thrown(StaffNotFoundException)
        exception.message == "Staff not found for id: 2"

    }

    def "should return a list of staff object"(){

        given:
        def staff = Staff
                .builder()
                .salonId(1L)
                .isAvailable(true)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        staffRepository.findAll() >> [staff,staff]

        when:
        int size = staffService.getAllStaff().size()

        then:
        size == 2
    }

    def "should return a list of staff by salon id"(){

        given:
        def staff = Staff
                .builder()
                .salonId(1L)
                .isAvailable(true)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        staffRepository.findAllBySalonId(1L) >> [staff,staff]

        when:
        int size = staffService.getListOfStaffBySalon(1L).size()

        then:
        size == 2

    }

    def "should return a list of available staff by salon id"(){

        given:
        def staff1 = Staff
                .builder()
                .salonId(1L)
                .isAvailable(true)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        def staff2 = Staff
                .builder()
                .salonId(1L)
                .isAvailable(false)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        staffRepository.findAllBySalonIdAndIsAvailable(1L,true) >> [staff1]

        when:
        int size = staffService.getListOfAvailableStaffBySalon(1L).size()

        then:
        size == 1

    }

    def "should update the availability false status of a staff"(){

        given:
        def staff = Staff
                .builder()
                .salonId(1L)
                .isAvailable(true)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        staffRepository.findById(1L) >> Optional.of(staff)
        staffRepository.save(_) >> staff

        when:
        def  response = staffService.updateStaffAvailability(1L)

        then:
        !response
    }

    def "should update the availability true status of a staff"(){

        given:
        def staff = Staff
                .builder()
                .salonId(1L)
                .isAvailable(false)
                .userId(1L)
                .contractNumber("836273")
                .address("dhaka")
                .employeementDate(null)
                .employeementType(null)
                .salary(4000.0)
                .build()

        staffRepository.findById(1L) >> Optional.of(staff)
        staffRepository.save(_) >> staff

        when:
        def  response = staffService.updateStaffAvailability(1L)

        then:
        response
    }

    def "should throw staff not found exception while updating the status availability by staff id"(){
        given:
        staffRepository.findById(2L) >> Optional.ofNullable(null)

        when:
        staffService.updateStaffAvailability(2L)

        then:
        def exception = thrown(StaffNotFoundException)
        exception.message == "staff not found for id:2"
    }
}