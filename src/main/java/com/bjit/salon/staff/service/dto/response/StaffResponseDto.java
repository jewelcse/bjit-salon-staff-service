package com.bjit.salon.staff.service.dto.response;


import com.bjit.salon.staff.service.entity.StaffActivity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class StaffResponseDto {
    private String address;
    private long salonId;
    private long userId;
    private boolean isAvailable;
    private String contractNumber;
    private double salary;
    private String employmentDate;
    private String employmentType;
    private List<StaffActivity> activities;
}
