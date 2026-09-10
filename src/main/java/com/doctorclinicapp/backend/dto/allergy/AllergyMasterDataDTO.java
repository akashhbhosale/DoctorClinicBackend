package com.doctorclinicapp.backend.dto.allergy;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyMasterDataDTO {

    private List<AllergySubstanceMasterDTO> substances;

    private List<AllergyManifestationMasterDTO> manifestations;

    // Criticality and Verification Status are small, fixed enums (not DB
    // masters) — same treatment as Encounter.jsx's diagnosisType/priority
    // dropdowns, which are hardcoded on the frontend. Kept here as plain
    // string lists too, so the frontend has a single master-data source of
    // truth instead of hardcoding these in two places.
    private List<String> criticalityOptions;

    private List<String> verificationStatusOptions;
}
