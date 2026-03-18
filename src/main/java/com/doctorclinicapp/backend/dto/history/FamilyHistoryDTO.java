package com.doctorclinicapp.backend.dto.history;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyHistoryDTO {

    private Long id;

    private Long patientId;

    private Long historyId;

    private String historyName;

    private Long relationshipId;

    private String relationshipName;
}