package com.bjit.salon.staff.service.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "salon_staff_activity")
public class StaffActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

    private long staffId;
    private long consumerId;
    private long reservationId;
    private LocalDate workingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private EWorkingStatus workingStatus;
}
