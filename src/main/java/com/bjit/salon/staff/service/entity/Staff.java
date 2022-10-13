package com.bjit.salon.staff.service.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "salon_staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;
    private String address;
    private long salonId;
    private long userId;
    private boolean isAvailable;
    private String contractNumber;
    private double salary;
    private String employeementDate;
    private String employeementType;

}
