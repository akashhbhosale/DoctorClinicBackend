package com.doctorclinicapp.backend.dto;

import com.doctorclinicapp.backend.enums.BloodGroup;
import com.doctorclinicapp.backend.model.Patient;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponse {

    private Long id;
    private String registrationNo;
    private String abhaId;

    private String fullName;
    private String gender;

    private LocalDate dateOfBirth;
    private Integer age;

    private BloodGroup bloodGroup;
    private String phoneNo;
    private String email;

    private String occupation;
    private String address;

    //  PATIENT RESPONSE METHOD
    public static PatientResponse fromEntity(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .registrationNo(patient.getRegistrationNo())
                .abhaId(patient.getAbhaId())
                .fullName(patient.getFullName())
                .gender(patient.getGender())
                .dateOfBirth(patient.getDateOfBirth())
                .age(patient.getAge())
                .bloodGroup(patient.getBloodGroup())
                .phoneNo(patient.getPhoneNo())
                .email(patient.getEmail())
                .occupation(patient.getOccupation())
                .address(patient.getAddress())
                .build();
    }
}
