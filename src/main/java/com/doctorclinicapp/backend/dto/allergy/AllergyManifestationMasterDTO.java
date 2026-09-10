package com.doctorclinicapp.backend.dto.allergy;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyManifestationMasterDTO {

    private Long id;
    private String manifestationName;
}
