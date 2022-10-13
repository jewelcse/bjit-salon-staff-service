package com.bjit.salon.staff.service.dto.request;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class StaffCreateDto {
    private String address;
    private long salonId;
    private long userId;
    private String contractNumber;
    private double salary;
    private String employeementDate;
    private String employeementType;
}
