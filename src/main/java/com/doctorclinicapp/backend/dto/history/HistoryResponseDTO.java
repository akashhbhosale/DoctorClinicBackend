package com.doctorclinicapp.backend.dto.history;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryResponseDTO {

    private List<PastMedicalHistoryDTO> pastMedicalHistory;

    private List<FamilyHistoryDTO> familyHistory;
}