package com.doctorclinicapp.backend.dto.allergy;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergySubstanceMasterDTO {

    private Long id;
    private String substanceName;
}
