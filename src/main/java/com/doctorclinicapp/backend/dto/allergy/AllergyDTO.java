package com.doctorclinicapp.backend.dto.allergy;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyDTO {

    private Long id;

    private Long patientId;

    private Long substanceId;
    private String substanceName;

    private String criticality;         // sent/received as plain string (e.g. "LOW")
    private String verificationStatus;   // sent/received as plain string (e.g. "CONFIRMED")

    private String comment;

    private List<Long> manifestationIds;
    private List<String> manifestationNames;
}
