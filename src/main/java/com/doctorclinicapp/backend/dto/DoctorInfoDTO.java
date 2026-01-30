package com.doctorclinicapp.backend.dto;

import com.doctorclinicapp.backend.enums.DoctorSpeciality;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorInfoDTO {

    private Long id;
    private String fullName;
    private String registrationNo;
    private String qualification;
    private DoctorSpeciality speciality;
}
