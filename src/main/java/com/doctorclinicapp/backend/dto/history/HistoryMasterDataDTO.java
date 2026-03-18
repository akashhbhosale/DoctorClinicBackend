package com.doctorclinicapp.backend.dto.history;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryMasterDataDTO {

    private List<PastMedicalHistoryMasterDTO> pastMedicalHistory;

    private List<FamilyHistoryMasterDTO> familyHistory;

    private List<RelationshipMasterDTO> relationships;

}