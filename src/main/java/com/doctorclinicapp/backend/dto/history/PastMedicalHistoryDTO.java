package com.doctorclinicapp.backend.dto.history;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PastMedicalHistoryDTO {

    private Long id;

    private Long patientId;

    private Long historyId;

    private String historyName;

    private Integer years;

    private Integer months;

    private Integer days;
}